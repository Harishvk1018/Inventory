package com.harish.Inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="product")

public class Product implements Serializable
{
	@Id
	@Column(name="id")
	@GenericGenerator(name = "b_info", strategy = "increment")
	@GeneratedValue(generator = "b_info")
	private int id;

	@Column(name="name")
	private String name;
	
	@Column(name="min_sell_quantity")
	private int minSellQuantity;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="price")
	private double price;
	
	@Column(name="date")
	private String date;
	
	@Column(name="agent_id")
	private int agentId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinSellQuantity() {
		return minSellQuantity;
	}

	public void setMinSellQuantity(int minSellQuantity) {
		this.minSellQuantity = minSellQuantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", minSellQuantity=" + minSellQuantity + ", quantity="
				+ quantity + ", price=" + price + ", date=" + date + ", agentId=" + agentId + "]";
	}

	

}
