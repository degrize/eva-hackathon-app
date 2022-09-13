package com.hackathon.eva.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hackathon.eva.domain.User;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private boolean activated = false;

    private String langKey;

    private String imageUrl;

    private String activationKey;

    private String resetKey;

    private Instant resetDate = null;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.langKey = user.getLangKey();
        this.activated = user.isActivated();
        this.imageUrl = user.getImageUrl();
        this.resetDate = user.getResetDate();
        this.activationKey = user.getActivationKey();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activationKey='" + activationKey + '\'' +
            ", resetKey='" + resetKey + '\'' +
            ", resetDate='" + resetDate + '\'' +
            "}";
    }
}
