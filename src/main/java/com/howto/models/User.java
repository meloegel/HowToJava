package com.howto.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.validation.constraints.Email;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;


    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // Primary email account of user. Could be used as the userid. Cannot be null and must be unique.
    @Column(nullable = false, unique = true)
    @Email
    private String primaryemail;


    // A list of emails for this user
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private List<Useremail> useremails = new ArrayList<>();

    // Part of the join relationship between user and role
    // connects users to the user role combination
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Set<UserRoles> roles = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private List<HowTo> howTos = new ArrayList<>();



    public User() {
    }

    public User(String username, String password, String primaryemail) {
        setUsername(username);
        setPassword(password);
        this.primaryemail = primaryemail;
    }


    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getPrimaryemail() {
        return primaryemail;
    }

    public void setPrimaryemail(String email) {
        this.primaryemail = email;
    }

    public List<Useremail> getUseremails()
    {
        return useremails;
    }

    public void setUseremails(List<Useremail> useremails)
    {
        this.useremails = useremails;
    }

    public Set<UserRoles> getRoles()
    {
        return roles;
    }

    public List<HowTo> getHowTos() {return howTos;}

    public void setHowTos(List<HowTo> howTos) {this.howTos = howTos;}

    // Setter for user role combinations
    // @param roles Change the list of user role combinations associated with this user to this one
    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }


    // Internally, user security requires a list of authorities, roles, that the user has. This method is a simple way to provide those.
    // Note that SimpleGrantedAuthority requests the format ROLE_role name all in capital letters!
    // @return The list of authorities, roles, this user object has
    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority() {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.roles) {
            String myRole = "ROLE_" + r.getRole()
                    .getName()
                    .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }
}
