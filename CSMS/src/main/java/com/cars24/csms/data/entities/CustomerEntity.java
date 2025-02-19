package com.cars24.csms.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="customers")
public class CustomerEntity {
    @Id
    private int customer_id;
    //    @NonNull
    private String name;
    //    @NonNull
    private String phone;
    private String email;
    private String address;
    private int userid;


}
