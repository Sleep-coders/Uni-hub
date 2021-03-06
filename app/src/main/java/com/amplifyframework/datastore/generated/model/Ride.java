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

/** This is an auto generated class representing the Ride type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Rides")
public final class Ride implements Model {
  public static final QueryField ID = field("Ride", "id");
  public static final QueryField OWNER_NAME = field("Ride", "owner_name");
  public static final QueryField OWNER_ID = field("Ride", "owner_id");
  public static final QueryField RIDE_DEPARTURE_TIME = field("Ride", "ride_departure_time");
  public static final QueryField AVAILABLE_SEATS = field("Ride", "available_seats");
  public static final QueryField COST = field("Ride", "cost");
  public static final QueryField CAR_IMAGE = field("Ride", "car_image");
  public static final QueryField CAR_INFO = field("Ride", "car_Info");
  public static final QueryField RIDE_EXPIRES_AT = field("Ride", "ride_expires_at");
  public static final QueryField RIDE_DATE = field("Ride", "ride_date");
  public static final QueryField RIDE_DESCRIPTION = field("Ride", "ride_description");
  public static final QueryField RIDE_ROUTE = field("Ride", "ride_route");
  public static final QueryField APP_USER_RIDES_ID = field("Ride", "appUserRidesId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String owner_name;
  private final @ModelField(targetType="String", isRequired = true) String owner_id;
  private final @ModelField(targetType="String", isRequired = true) String ride_departure_time;
  private final @ModelField(targetType="Int", isRequired = true) Integer available_seats;
  private final @ModelField(targetType="Float", isRequired = true) Double cost;
  private final @ModelField(targetType="String", isRequired = true) String car_image;
  private final @ModelField(targetType="String", isRequired = true) String car_Info;
  private final @ModelField(targetType="String", isRequired = true) String ride_expires_at;
  private final @ModelField(targetType="String", isRequired = true) String ride_date;
  private final @ModelField(targetType="String", isRequired = true) String ride_description;
  private final @ModelField(targetType="String") String ride_route;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String appUserRidesId;
  public String getId() {
      return id;
  }
  
  public String getOwnerName() {
      return owner_name;
  }
  
  public String getOwnerId() {
      return owner_id;
  }
  
  public String getRideDepartureTime() {
      return ride_departure_time;
  }
  
  public Integer getAvailableSeats() {
      return available_seats;
  }
  
  public Double getCost() {
      return cost;
  }
  
  public String getCarImage() {
      return car_image;
  }
  
  public String getCarInfo() {
      return car_Info;
  }
  
  public String getRideExpiresAt() {
      return ride_expires_at;
  }
  
  public String getRideDate() {
      return ride_date;
  }
  
  public String getRideDescription() {
      return ride_description;
  }
  
  public String getRideRoute() {
      return ride_route;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getAppUserRidesId() {
      return appUserRidesId;
  }
  
  private Ride(String id, String owner_name, String owner_id, String ride_departure_time, Integer available_seats, Double cost, String car_image, String car_Info, String ride_expires_at, String ride_date, String ride_description, String ride_route, String appUserRidesId) {
    this.id = id;
    this.owner_name = owner_name;
    this.owner_id = owner_id;
    this.ride_departure_time = ride_departure_time;
    this.available_seats = available_seats;
    this.cost = cost;
    this.car_image = car_image;
    this.car_Info = car_Info;
    this.ride_expires_at = ride_expires_at;
    this.ride_date = ride_date;
    this.ride_description = ride_description;
    this.ride_route = ride_route;
    this.appUserRidesId = appUserRidesId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Ride ride = (Ride) obj;
      return ObjectsCompat.equals(getId(), ride.getId()) &&
              ObjectsCompat.equals(getOwnerName(), ride.getOwnerName()) &&
              ObjectsCompat.equals(getOwnerId(), ride.getOwnerId()) &&
              ObjectsCompat.equals(getRideDepartureTime(), ride.getRideDepartureTime()) &&
              ObjectsCompat.equals(getAvailableSeats(), ride.getAvailableSeats()) &&
              ObjectsCompat.equals(getCost(), ride.getCost()) &&
              ObjectsCompat.equals(getCarImage(), ride.getCarImage()) &&
              ObjectsCompat.equals(getCarInfo(), ride.getCarInfo()) &&
              ObjectsCompat.equals(getRideExpiresAt(), ride.getRideExpiresAt()) &&
              ObjectsCompat.equals(getRideDate(), ride.getRideDate()) &&
              ObjectsCompat.equals(getRideDescription(), ride.getRideDescription()) &&
              ObjectsCompat.equals(getRideRoute(), ride.getRideRoute()) &&
              ObjectsCompat.equals(getCreatedAt(), ride.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), ride.getUpdatedAt()) &&
              ObjectsCompat.equals(getAppUserRidesId(), ride.getAppUserRidesId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getOwnerName())
      .append(getOwnerId())
      .append(getRideDepartureTime())
      .append(getAvailableSeats())
      .append(getCost())
      .append(getCarImage())
      .append(getCarInfo())
      .append(getRideExpiresAt())
      .append(getRideDate())
      .append(getRideDescription())
      .append(getRideRoute())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getAppUserRidesId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Ride {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("owner_name=" + String.valueOf(getOwnerName()) + ", ")
      .append("owner_id=" + String.valueOf(getOwnerId()) + ", ")
      .append("ride_departure_time=" + String.valueOf(getRideDepartureTime()) + ", ")
      .append("available_seats=" + String.valueOf(getAvailableSeats()) + ", ")
      .append("cost=" + String.valueOf(getCost()) + ", ")
      .append("car_image=" + String.valueOf(getCarImage()) + ", ")
      .append("car_Info=" + String.valueOf(getCarInfo()) + ", ")
      .append("ride_expires_at=" + String.valueOf(getRideExpiresAt()) + ", ")
      .append("ride_date=" + String.valueOf(getRideDate()) + ", ")
      .append("ride_description=" + String.valueOf(getRideDescription()) + ", ")
      .append("ride_route=" + String.valueOf(getRideRoute()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("appUserRidesId=" + String.valueOf(getAppUserRidesId()))
      .append("}")
      .toString();
  }
  
  public static OwnerNameStep builder() {
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
  public static Ride justId(String id) {
    return new Ride(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      owner_name,
      owner_id,
      ride_departure_time,
      available_seats,
      cost,
      car_image,
      car_Info,
      ride_expires_at,
      ride_date,
      ride_description,
      ride_route,
      appUserRidesId);
  }
  public interface OwnerNameStep {
    OwnerIdStep ownerName(String ownerName);
  }
  

  public interface OwnerIdStep {
    RideDepartureTimeStep ownerId(String ownerId);
  }
  

  public interface RideDepartureTimeStep {
    AvailableSeatsStep rideDepartureTime(String rideDepartureTime);
  }
  

  public interface AvailableSeatsStep {
    CostStep availableSeats(Integer availableSeats);
  }
  

  public interface CostStep {
    CarImageStep cost(Double cost);
  }
  

  public interface CarImageStep {
    CarInfoStep carImage(String carImage);
  }
  

  public interface CarInfoStep {
    RideExpiresAtStep carInfo(String carInfo);
  }
  

  public interface RideExpiresAtStep {
    RideDateStep rideExpiresAt(String rideExpiresAt);
  }
  

  public interface RideDateStep {
    RideDescriptionStep rideDate(String rideDate);
  }
  

  public interface RideDescriptionStep {
    BuildStep rideDescription(String rideDescription);
  }
  

  public interface BuildStep {
    Ride build();
    BuildStep id(String id);
    BuildStep rideRoute(String rideRoute);
    BuildStep appUserRidesId(String appUserRidesId);
  }
  

  public static class Builder implements OwnerNameStep, OwnerIdStep, RideDepartureTimeStep, AvailableSeatsStep, CostStep, CarImageStep, CarInfoStep, RideExpiresAtStep, RideDateStep, RideDescriptionStep, BuildStep {
    private String id;
    private String owner_name;
    private String owner_id;
    private String ride_departure_time;
    private Integer available_seats;
    private Double cost;
    private String car_image;
    private String car_Info;
    private String ride_expires_at;
    private String ride_date;
    private String ride_description;
    private String ride_route;
    private String appUserRidesId;
    @Override
     public Ride build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Ride(
          id,
          owner_name,
          owner_id,
          ride_departure_time,
          available_seats,
          cost,
          car_image,
          car_Info,
          ride_expires_at,
          ride_date,
          ride_description,
          ride_route,
          appUserRidesId);
    }
    
    @Override
     public OwnerIdStep ownerName(String ownerName) {
        Objects.requireNonNull(ownerName);
        this.owner_name = ownerName;
        return this;
    }
    
    @Override
     public RideDepartureTimeStep ownerId(String ownerId) {
        Objects.requireNonNull(ownerId);
        this.owner_id = ownerId;
        return this;
    }
    
    @Override
     public AvailableSeatsStep rideDepartureTime(String rideDepartureTime) {
        Objects.requireNonNull(rideDepartureTime);
        this.ride_departure_time = rideDepartureTime;
        return this;
    }
    
    @Override
     public CostStep availableSeats(Integer availableSeats) {
        Objects.requireNonNull(availableSeats);
        this.available_seats = availableSeats;
        return this;
    }
    
    @Override
     public CarImageStep cost(Double cost) {
        Objects.requireNonNull(cost);
        this.cost = cost;
        return this;
    }
    
    @Override
     public CarInfoStep carImage(String carImage) {
        Objects.requireNonNull(carImage);
        this.car_image = carImage;
        return this;
    }
    
    @Override
     public RideExpiresAtStep carInfo(String carInfo) {
        Objects.requireNonNull(carInfo);
        this.car_Info = carInfo;
        return this;
    }
    
    @Override
     public RideDateStep rideExpiresAt(String rideExpiresAt) {
        Objects.requireNonNull(rideExpiresAt);
        this.ride_expires_at = rideExpiresAt;
        return this;
    }
    
    @Override
     public RideDescriptionStep rideDate(String rideDate) {
        Objects.requireNonNull(rideDate);
        this.ride_date = rideDate;
        return this;
    }
    
    @Override
     public BuildStep rideDescription(String rideDescription) {
        Objects.requireNonNull(rideDescription);
        this.ride_description = rideDescription;
        return this;
    }
    
    @Override
     public BuildStep rideRoute(String rideRoute) {
        this.ride_route = rideRoute;
        return this;
    }
    
    @Override
     public BuildStep appUserRidesId(String appUserRidesId) {
        this.appUserRidesId = appUserRidesId;
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
    private CopyOfBuilder(String id, String ownerName, String ownerId, String rideDepartureTime, Integer availableSeats, Double cost, String carImage, String carInfo, String rideExpiresAt, String rideDate, String rideDescription, String rideRoute, String appUserRidesId) {
      super.id(id);
      super.ownerName(ownerName)
        .ownerId(ownerId)
        .rideDepartureTime(rideDepartureTime)
        .availableSeats(availableSeats)
        .cost(cost)
        .carImage(carImage)
        .carInfo(carInfo)
        .rideExpiresAt(rideExpiresAt)
        .rideDate(rideDate)
        .rideDescription(rideDescription)
        .rideRoute(rideRoute)
        .appUserRidesId(appUserRidesId);
    }
    
    @Override
     public CopyOfBuilder ownerName(String ownerName) {
      return (CopyOfBuilder) super.ownerName(ownerName);
    }
    
    @Override
     public CopyOfBuilder ownerId(String ownerId) {
      return (CopyOfBuilder) super.ownerId(ownerId);
    }
    
    @Override
     public CopyOfBuilder rideDepartureTime(String rideDepartureTime) {
      return (CopyOfBuilder) super.rideDepartureTime(rideDepartureTime);
    }
    
    @Override
     public CopyOfBuilder availableSeats(Integer availableSeats) {
      return (CopyOfBuilder) super.availableSeats(availableSeats);
    }
    
    @Override
     public CopyOfBuilder cost(Double cost) {
      return (CopyOfBuilder) super.cost(cost);
    }
    
    @Override
     public CopyOfBuilder carImage(String carImage) {
      return (CopyOfBuilder) super.carImage(carImage);
    }
    
    @Override
     public CopyOfBuilder carInfo(String carInfo) {
      return (CopyOfBuilder) super.carInfo(carInfo);
    }
    
    @Override
     public CopyOfBuilder rideExpiresAt(String rideExpiresAt) {
      return (CopyOfBuilder) super.rideExpiresAt(rideExpiresAt);
    }
    
    @Override
     public CopyOfBuilder rideDate(String rideDate) {
      return (CopyOfBuilder) super.rideDate(rideDate);
    }
    
    @Override
     public CopyOfBuilder rideDescription(String rideDescription) {
      return (CopyOfBuilder) super.rideDescription(rideDescription);
    }
    
    @Override
     public CopyOfBuilder rideRoute(String rideRoute) {
      return (CopyOfBuilder) super.rideRoute(rideRoute);
    }
    
    @Override
     public CopyOfBuilder appUserRidesId(String appUserRidesId) {
      return (CopyOfBuilder) super.appUserRidesId(appUserRidesId);
    }
  }
  
}
