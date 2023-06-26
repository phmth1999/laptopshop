package com.phmth.laptopshop.entity;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phmth.laptopshop.enums.AuthenticationType;
import com.phmth.laptopshop.enums.StateUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private RoleEntity role;
	
	
	
	@Column(name = "fullname", length = 100)
	private String fullname;

	@Column(name = "email", length = 100, unique = true)
	private String email;
	
	@Column(name = "hash_password", length = 256)
	private String hash_password;

	
	
	@Column(name = "sex", length = 20)
	private String sex;
	
	@Column(name = "birthday", length = 20)
	private String birthday;
	
	@Column(name = "address")
	private String address;

	@Column(name = "img")
	private String img;
	
	
	
	@Column(name = "register_token")
	private String registerToken;
	
	@Column(name = "register_token_expire_at")
	private Long registerTokenExpireAt;
	
	@Column(name = "reset_password_token")
	private String resetPasswordToken;
	
	@Column(name = "reset_password_token_expire_at")
	private Long resetPasswordTokenExpireAt;
	
	
	
	@Column(name = "created_at", length = 20)
	private Date created_at;

	@Column(name = "updated_at", length = 20)
	private Date update_at;
	
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "state_user")
	private StateUser stateUser;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "auth_type")
    private AuthenticationType authType;
 
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CommentEntity> comments;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<OrderEntity> orders;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CheckoutEntity> checkouts;
	
	public boolean isEmpty()  {
	    for (Field field : this.getClass().getDeclaredFields()) {
	        try {
	            field.setAccessible(true);
	            if (field.get(this)!=null) {
	                return false;
	            }
	        } catch (Exception e) {
	          System.out.println("Exception occured in processing");
	        }
	    }
	    return true;
	}

}
