package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Car type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Cars")
public final class Car implements Model {
  public static final QueryField ID = field("Car", "id");
  public static final QueryField CAR_MODEL = field("Car", "car_model");
  public static final QueryField CAR_IMG = field("Car", "car_img");
  public static final QueryField CAR_SEATS_NUM = field("Car", "car_seats_num");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String car_model;
  private final @ModelField(targetType="String", isRequired = true) String car_img;
  private final @ModelField(targetType="Int", isRequired = true) Integer car_seats_num;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getCarModel() {
      return car_model;
  }
  
  public String getCarImg() {
      return car_img;
  }
  
  public Integer getCarSeatsNum() {
      return car_seats_num;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Car(String id, String car_model, String car_img, Integer car_seats_num) {
    this.id = id;
    this.car_model = car_model;
    this.car_img = car_img;
    this.car_seats_num = car_seats_num;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Car car = (Car) obj;
      return ObjectsCompat.equals(getId(), car.getId()) &&
              ObjectsCompat.equals(getCarModel(), car.getCarModel()) &&
              ObjectsCompat.equals(getCarImg(), car.getCarImg()) &&
              ObjectsCompat.equals(getCarSeatsNum(), car.getCarSeatsNum()) &&
              ObjectsCompat.equals(getCreatedAt(), car.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), car.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCarModel())
      .append(getCarImg())
      .append(getCarSeatsNum())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Car {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("car_model=" + String.valueOf(getCarModel()) + ", ")
      .append("car_img=" + String.valueOf(getCarImg()) + ", ")
      .append("car_seats_num=" + String.valueOf(getCarSeatsNum()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static CarModelStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Car justId(String id) {
    return new Car(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      car_model,
      car_img,
      car_seats_num);
  }
  public interface CarModelStep {
    CarImgStep carModel(String carModel);
  }
  

  public interface CarImgStep {
    CarSeatsNumStep carImg(String carImg);
  }
  

  public interface CarSeatsNumStep {
    BuildStep carSeatsNum(Integer carSeatsNum);
  }
  

  public interface BuildStep {
    Car build();
    BuildStep id(String id);
  }
  

  public static class Builder implements CarModelStep, CarImgStep, CarSeatsNumStep, BuildStep {
    private String id;
    private String car_model;
    private String car_img;
    private Integer car_seats_num;
    @Override
     public Car build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Car(
          id,
          car_model,
          car_img,
          car_seats_num);
    }
    
    @Override
     public CarImgStep carModel(String carModel) {
        Objects.requireNonNull(carModel);
        this.car_model = carModel;
        return this;
    }
    
    @Override
     public CarSeatsNumStep carImg(String carImg) {
        Objects.requireNonNull(carImg);
        this.car_img = carImg;
        return this;
    }
    
    @Override
     public BuildStep carSeatsNum(Integer carSeatsNum) {
        Objects.requireNonNull(carSeatsNum);
        this.car_seats_num = carSeatsNum;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String carModel, String carImg, Integer carSeatsNum) {
      super.id(id);
      super.carModel(carModel)
        .carImg(carImg)
        .carSeatsNum(carSeatsNum);
    }
    
    @Override
     public CopyOfBuilder carModel(String carModel) {
      return (CopyOfBuilder) super.carModel(carModel);
    }
    
    @Override
     public CopyOfBuilder carImg(String carImg) {
      return (CopyOfBuilder) super.carImg(carImg);
    }
    
    @Override
     public CopyOfBuilder carSeatsNum(Integer carSeatsNum) {
      return (CopyOfBuilder) super.carSeatsNum(carSeatsNum);
    }
  }
  
}
