package cz.fi.muni.carshop.entities;

import java.awt.Color;

import cz.fi.muni.carshop.enums.CarTypes;

public class Car {

	private Color color;
	private CarTypes type;
	private int constructionYear;
	private int price;

	public Car(Color color, CarTypes type, int constructionYear, int price) {
		super();
		if (price < 0) {
			throw new IllegalArgumentException("The buyer will have to pay to the seller!! Not the other way around ;)");
		}
		this.color = color;
		this.type = type;
		this.constructionYear = constructionYear;
		this.price = price;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public CarTypes getType() {
		return type;
	}
	
	public int getConstructionYear() {
		return constructionYear;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	
}
