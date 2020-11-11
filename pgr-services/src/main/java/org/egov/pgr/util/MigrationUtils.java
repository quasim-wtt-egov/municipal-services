package org.egov.pgr.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.pgr.config.PGRConfiguration;
import org.egov.pgr.repository.ServiceRequestRepository;
import org.egov.pgr.web.models.RequestInfoWrapper;
import org.egov.pgr.web.models.User;
import org.egov.pgr.web.models.user.UserDetailResponse;
import org.egov.pgr.web.models.user.UserSearchRequest;
import org.egov.pgr.web.models.workflow.BusinessServiceResponse;
import org.egov.pgr.web.models.workflow.State;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.egov.pgr.util.PGRConstants.PGR_BUSINESSSERVICE;
import static org.egov.pgr.util.PGRConstants.USERTYPE_CITIZEN;

@Component
public class MigrationUtils {


    private UserUtils userUtils;

    private PGRConfiguration config;

    private ObjectMapper mapper;

    private ServiceRequestRepository repository;

    @Autowired
    public MigrationUtils(UserUtils userUtils, PGRConfiguration config, ObjectMapper mapper, ServiceRequestRepository repository) {
        this.userUtils = userUtils;
        this.config = config;
        this.mapper = mapper;
        this.repository = repository;
    }



    public Map<Long, String> getIdtoUUIDMap(List<String> ids) {

        /**
         * calls the user search API based on the given list of user uuids
         * @param uuids
         * @return
         */

        UserSearchRequest userSearchRequest = new UserSearchRequest();

        if (!CollectionUtils.isEmpty(ids))
            userSearchRequest.setId(ids);


        StringBuilder uri = new StringBuilder(config.getUserHost()).append(config.getUserSearchEndpoint());
        UserDetailResponse userDetailResponse = userUtils.userCall(userSearchRequest, uri);
        List<User> users = userDetailResponse.getUser();

        if (CollectionUtils.isEmpty(users))
            throw new CustomException("USER_NOT_FOUND", "No user found for the uuids");

        Map<Long, String> idToUuidMap = users.stream().collect(Collectors.toMap(User::getId, User::getUuid));

        if (idToUuidMap.keySet().size() != ids.size())
            throw new CustomException("UUID_NOT_FOUND", "Number of ids searched: " + ids.size() + " uuids returned: " + idToUuidMap.keySet());

        return idToUuidMap;

    }




    public Map<String,String> getStatusToUUIDMap(String tenantId) {
        StringBuilder url = getSearchURLWithParams(tenantId, PGR_BUSINESSSERVICE);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(new RequestInfo()).build();
        Object result = repository.fetchResult(url, requestInfoWrapper);
        BusinessServiceResponse response = null;
        try {
            response = mapper.convertValue(result, BusinessServiceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow business service search");
        }

        if (CollectionUtils.isEmpty(response.getBusinessServices()))
            throw new CustomException("BUSINESSSERVICE_NOT_FOUND", "The businessService " + PGR_BUSINESSSERVICE + " is not found");

        Map<String,String> statusToUUIDMap = response.getBusinessServices().get(0).getStates().stream()
                .collect(Collectors.toMap(State::getState,State::getUuid));

        return statusToUUIDMap;
    }



    private StringBuilder getSearchURLWithParams(String tenantId, String businessService) {

        StringBuilder url = new StringBuilder(config.getWfHost());
        url.append(config.getWfBusinessServiceSearchPath());
        url.append("?tenantId=");
        url.append(tenantId);
        url.append("&businessservices=");
        url.append(businessService);
        return url;
    }


}