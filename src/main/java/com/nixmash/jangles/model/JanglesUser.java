package com.nixmash.jangles.model;

import com.fasterxml.jackson.annotation.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.net.URI;
import java.sql.Timestamp;

import static org.glassfish.jersey.linking.InjectLink.Style.ABSOLUTE;

@XmlRootElement()
@JsonPropertyOrder({"userId", "userName", "displayName", "dateCreated", "isActive", "link", "users"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JanglesUser implements Serializable {

    // region Constructors

    public JanglesUser() {
    }

    public JanglesUser(String userName, String password, String displayName) {
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
    }

    // endregion

    // region properties

    @InjectLink(value = "/users", style = ABSOLUTE, condition = "${instance.showUsersLink}", rel="parent")
    @JsonProperty(value = "users")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    private Link usersUri;

    @InjectLink(value = "/users/{userId}", style = ABSOLUTE, bindings = {
            @Binding(name = "id", value = "${resource.userId}")})
    @XmlElement(name = "link")
    private URI userUri;

    private int userId;
    private String userName;
    private String displayName;

    @JsonIgnore
    private String password;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    private Timestamp dateCreated;
    private Boolean isActive = true;

    @JsonIgnore
    private Boolean showUsersLink = false;

    // endregion

    // region getters/setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Link getUsersUri() {
        return usersUri;
    }

    public void setUsersUri(Link usersUri) {
        this.usersUri = usersUri;
    }

    public URI getUserUri() {
        return userUri;
    }

    public void setUserUri(URI userUri) {
        this.userUri = userUri;
    }

    public Boolean getShowUsersLink() {
        return showUsersLink;
    }

    public void setShowUsersLink(Boolean showUsersLink) {
        this.showUsersLink = showUsersLink;
    }

    // endregion

    @Override
    public String toString() {
        return "JanglesUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", displayName='" + displayName + '\'' +
                ", dateCreated=" + dateCreated +
                ", isActive=" + isActive +
                '}';
    }

}


	
