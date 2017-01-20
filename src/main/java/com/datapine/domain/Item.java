package com.datapine.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@Entity
@AutoProperty
public class Item {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String owner;
	private String name;
	private String description;

	public Item() {}

	public Item(String owner, String name, String description) {
	    this.owner = owner;
	    this.name = name;
	    this.description = description;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

}
