package com.emp.details.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tb_employees")
public class Employees {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

   // @NotNull(message = "GenderType cannot be blank")
	@ApiModelProperty(value = "Valid status", required = true, allowableValues = "MALE,FEMALE,TRANSGENDER")
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
    private GenderType gender;

   
    @Column(name = "country_id")
    private Long country;

    
    @Column(name = "state_id")
    private Long state;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    private String city;

    @Column(name = "about_me", columnDefinition = "TEXT")
    private String aboutMe;

    @Column(name = "is_active")
    private boolean active;

    @CreatedDate
	@Column(name = "created_at",nullable = false, updatable = false)
	private LocalDateTime createdAt; 
	
	@LastModifiedBy
	@Column(name = "updated_by", nullable = false)
	private long updatedBy; 
	
	@LastModifiedDate
	@Column(name = "updated_at",nullable = false, updatable = false)
	private LocalDateTime updatedAt; 
	
	@CreatedBy
	@Column(name = "created_by", nullable = false)
	private long createdBy;
}
