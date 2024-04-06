package com.company.carpark.datamodel.constant;

public enum Type {

  SPECIAL("special"),
  TRUCKS("trucks"),
  PASSENGER("passenger");

  private String value;
  Type(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
