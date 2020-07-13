package org.egov.waterconnection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.Role;
import org.egov.waterconnection.model.users.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionHolderInfo extends User {

    @JsonProperty("ownerInfoUuid")
    private String ownerInfoUuid;

    @NotNull
    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("fatherOrHusbandName")
    private String fatherOrHusbandName;

    @JsonProperty("correspondenceAddress")
    private String correspondenceAddress;

    @JsonProperty("isPrimaryOwner")
    private Boolean isPrimaryOwner;

    @JsonProperty("ownerShipPercentage")
    private Double ownerShipPercentage;

    @NotNull
    @JsonProperty("ownerType")
    private String ownerType;

    @JsonProperty("institutionId")
    private String institutionId;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("documents")
    @Valid
    private List<Document> documents;

    @JsonProperty("relationship")
    private Relationship relationship;

    @Builder
    public ConnectionHolderInfo(Long id, String uuid, String userName, String password, String salutation, String name,
                     String gender, String mobileNumber, String emailId, String altContactNumber, String pan,
                     String aadhaarNumber, String permanentAddress, String permanentCity, String permanentPincode,
                     String correspondenceCity, String correspondencePincode, String correspondenceAddress, Boolean active,
                     Long dob, Long pwdExpiryDate, String locale, String type, String signature, Boolean accountLocked,
                     List<Role> roles, String fatherOrHusbandName, String bloodGroup, String identificationMark, String photo,
                     String createdBy, Long createdDate, String lastModifiedBy, Long lastModifiedDate, String tenantId,
                     String ownerInfoUuid, String mobileNumber2, String gender2, String fatherOrHusbandName2,
                     String correspondenceAddress2, Boolean isPrimaryOwner, Double ownerShipPercentage, String ownerType,
                     String institutionId, Status status, List<Document> documents, Relationship relationship) {
        super(id, uuid, userName, password, salutation, name, gender, mobileNumber, emailId, altContactNumber, pan,
                aadhaarNumber, permanentAddress, permanentCity, permanentPincode, correspondenceCity,
                correspondencePincode, correspondenceAddress, active, dob, pwdExpiryDate, locale, type, signature,
                accountLocked, roles, fatherOrHusbandName, bloodGroup, identificationMark, photo, createdBy,
                createdDate, lastModifiedBy, lastModifiedDate, tenantId);
        this.ownerInfoUuid = ownerInfoUuid;
        this.mobileNumber = mobileNumber2;
        gender = gender2;
        fatherOrHusbandName = fatherOrHusbandName2;
        correspondenceAddress = correspondenceAddress2;
        this.isPrimaryOwner = isPrimaryOwner;
        this.ownerShipPercentage = ownerShipPercentage;
        this.ownerType = ownerType;
        this.institutionId = institutionId;
        this.status = status;
        this.documents = documents;
        this.relationship = relationship;
    }

    public ConnectionHolderInfo addDocumentsItem(Document documentsItem) {
        if (this.documents == null) {
            this.documents = new ArrayList<>();
        }
        this.documents.add(documentsItem);
        return this;
    }

    /**
     * Populates Owner fields from the given User object
     *
     * @param user User object obtained from user service
     */
    public void addUserDetail(User user) {
        this.setLastModifiedDate(user.getLastModifiedDate());
        this.setLastModifiedBy(user.getLastModifiedBy());
        this.setCreatedBy(user.getCreatedBy());
        this.setCreatedDate(user.getCreatedDate());
        this.setUserName(user.getUserName());
        this.setPassword(user.getPassword());
        this.setSalutation(user.getSalutation());
        this.setName(user.getName());
        this.setGender(user.getGender());
        this.setMobileNumber(user.getMobileNumber());
        this.setEmailId(user.getEmailId());
        this.setAltContactNumber(user.getAltContactNumber());
        this.setPan(user.getPan());
        this.setAadhaarNumber(user.getAadhaarNumber());
        this.setPermanentAddress(user.getPermanentAddress());
        this.setPermanentCity(user.getPermanentCity());
        this.setPermanentPincode(user.getPermanentPincode());
        this.setCorrespondenceAddress(user.getCorrespondenceAddress());
        this.setCorrespondenceCity(user.getCorrespondenceCity());
        this.setCorrespondencePincode(user.getCorrespondencePincode());
        this.setActive(user.getActive());
        this.setDob(user.getDob());
        this.setPwdExpiryDate(user.getPwdExpiryDate());
        this.setLocale(user.getLocale());
        this.setType(user.getType());
        this.setAccountLocked(user.getAccountLocked());
        this.setRoles(user.getRoles());
        this.setFatherOrHusbandName(user.getFatherOrHusbandName());
        this.setBloodGroup(user.getBloodGroup());
        this.setIdentificationMark(user.getIdentificationMark());
        this.setPhoto(user.getPhoto());
        this.setTenantId(user.getTenantId());
    }
    public org.egov.common.contract.request.User toCommonUser(){
        org.egov.common.contract.request.User commonUser = new org.egov.common.contract.request.User();
        commonUser.setId(this.getId());
        commonUser.setUserName(this.getUserName());
        commonUser.setName(this.getName());
        commonUser.setType(this.getType());
        commonUser.setMobileNumber(this.getMobileNumber());
        commonUser.setEmailId(this.getEmailId());
        commonUser.setRoles(this.getRoles());
        commonUser.setTenantId(this.getTenantId());
        commonUser.setUuid(this.getUuid());
        return commonUser;
    }
}