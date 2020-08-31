package org.egov.pgr.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.TimeZone;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
public class PGRConfiguration {




    @Value("${app.timezone}")
    private String timeZone;

    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

    @Bean
    @Autowired
    public MappingJackson2HttpMessageConverter jacksonConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    // User Config
    @Value("${egov.user.host}")
    private String userHost;

    @Value("${egov.user.context.path}")
    private String userContextPath;

    @Value("${egov.user.create.path}")
    private String userCreateEndpoint;

    @Value("${egov.user.search.path}")
    private String userSearchEndpoint;

    @Value("${egov.user.update.path}")
    private String userUpdateEndpoint;

    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;

    @Value("${egov.idgen.path}")
    private String idGenPath;

    @Value("${egov.idgen.pgr.serviceRequestId.name}")
    private String serviceRequestIdGenName;

    @Value("${egov.idgen.pgr.serviceRequestId.format}")
    private String serviceRequestIdGenFormat;

    //Workflow Config
    @Value("${pgr.business.codes}")
    private List<String> businessServiceList;

    @Value("${egov.workflow.host}")
    private String wfHost;

    @Value("${egov.workflow.transition.path}")
    private String wfTransitionPath;

    @Value("${egov.workflow.businessservice.search.path}")
    private String wfBusinessServiceSearchPath;

    @Value("${egov.workflow.processinstance.search.path}")
    private String wfProcessInstanceSearchPath;

    @Value("${is.workflow.enabled}")
    private Boolean isWorkflowEnabled;


    // PGR Variables

    @Value("${pgr.complain.idle.time}")
    private Long complainMaxIdleTime;

    @Value("${pgr.kafka.create.topic}")
    private String createTopic;

    @Value("${pgr.kafka.update.topic}")
    private String updateTopic;

    @Value("${pgr.default.offset}")
    private Integer defaultOffset;

    @Value("${pgr.default.limit}")
    private Integer defaultLimit;

    @Value("${pgr.search.max.limit}")
    private Integer maxLimit;


    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    //HRMS
    @Value("${egov.hrms.host}")
    private String hrmsHost;

    @Value("${egov.hrms.search.endpoint}")
    private String hrmsEndPoint;

    //Notification
    @Value("${egov.user.event.notification.enabled}")
    private Boolean isUserEventsNotificationEnabled;

    @Value("${notification.sms.enabled}")
    private Boolean isSMSEnabled;

    @Value("${egov.localization.statelevel}")
    private Boolean isLocalizationStateLevel;

    @Value("${egov.localization.host}")
    private String localizationHost;

    @Value("${egov.localization.context.path}")
    private String localizationContextPath;

    @Value("${egov.localization.search.endpoint}")
    private String localizationSearchEndpoint;

    @Value("${kafka.topics.notification.sms}")
    private String smsNotifTopic;

    @Value("${egov.usr.events.create.topic}")
    private String saveUserEventsTopic;
}
