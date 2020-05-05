package org.egov.bpa.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.egov.bpa.service.BPAService;
import org.egov.bpa.util.BPAUtil;
import org.egov.bpa.util.ResponseInfoFactory;
import org.egov.bpa.web.model.BPA;
import org.egov.bpa.web.model.BPARequest;
import org.egov.bpa.web.model.BPAResponse;
import org.egov.bpa.web.model.BPASearchCriteria;
import org.egov.bpa.web.model.LandInfo;
import org.egov.bpa.web.model.LandRequest;
import org.egov.bpa.web.model.LandResponse;
import org.egov.bpa.web.model.LandSearchCriteria;
import org.egov.bpa.web.model.RequestInfoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BPAController {

	@Autowired
	private BPAService bpaService;

	@Autowired
	private BPAUtil bpaUtil;

	@Autowired
	private ResponseInfoFactory responseInfoFactory;

	@PostMapping(value = "land/_create")
	public ResponseEntity<LandResponse> landCreate(@Valid @RequestBody LandRequest landRequest) {
		bpaUtil.defaultJsonPathConfig();
		LandInfo landInfo = bpaService.landCreate(landRequest);
		LandResponse response = LandResponse.builder().landInfo(landInfo)
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(landRequest.getRequestInfo(), true))
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	@PostMapping(value = "land/_update")
	public ResponseEntity<LandResponse> landUpdate(@Valid @RequestBody LandRequest landRequest) {
		BPA bpa = bpaService.landUpdate(landRequest);
		LandInfo landInfo = bpaService.landCreate(landRequest);
		LandResponse response = LandResponse.builder().landInfo(landInfo)
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(landRequest.getRequestInfo(), true))
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "land/_search")
	public ResponseEntity<LandResponse> search(@Valid @RequestBody RequestInfoWrapper requestInfoWrapper,
			@Valid @ModelAttribute LandSearchCriteria criteria) {

		LandInfo landInfo = bpaService.landSearch(criteria, requestInfoWrapper.getRequestInfo());
		LandResponse response = LandResponse.builder().landInfo(landInfo)
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true))
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "bpa/_create")
	public ResponseEntity<BPAResponse> create(@Valid @RequestBody BPARequest bpaRequest) {
		bpaUtil.defaultJsonPathConfig();
		BPA bpa = bpaService.create(bpaRequest);
		List<BPA> bpas = new ArrayList<BPA>();
		bpas.add(bpa);
		BPAResponse response = BPAResponse.builder().BPA(bpas)
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(bpaRequest.getRequestInfo(), true))
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "bpa/_update")
	public ResponseEntity<BPAResponse> update(@Valid @RequestBody BPARequest bpaRequest) {
		BPA bpa = bpaService.update(bpaRequest);
		List<BPA> bpas = new ArrayList<BPA>();
		bpas.add(bpa);
		BPAResponse response = BPAResponse.builder().BPA(bpas)
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(bpaRequest.getRequestInfo(), true))
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping(value = "bpa/_search")
	public ResponseEntity<BPAResponse> search(@Valid @RequestBody RequestInfoWrapper requestInfoWrapper,
			@Valid @ModelAttribute BPASearchCriteria criteria) {

		List<BPA> bpas = bpaService.search(criteria, requestInfoWrapper.getRequestInfo());

		BPAResponse response = BPAResponse.builder().BPA(bpas).responseInfo(
				responseInfoFactory.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true))
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


}