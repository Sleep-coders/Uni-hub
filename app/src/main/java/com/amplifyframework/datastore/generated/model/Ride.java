package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
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
@Index(name = "byOwner", fields = {"owner_id"})
public final class Ride implements Model {
  public static final QueryField ID = field("Ride", "id");
  public static final QueryField OWNER_ID = field("Ride", "owner_id");
  public static final QueryField RIDE_DEPARTURE_TIME = field("Ride", "ride_departure_time");
  public static final QueryField AVAILABLE_SEATS = field("Ride", "available_seats");
  public static final QueryField RIDE_ROUTE = field("Ride", "ride_route");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String owner_id;
  private final @ModelField(targetType="String", isRequired = true) String ride_departure_time;
  private final @ModelField(targetType="Int", isRequired = true) Integer available_seats;
  private final @ModelField(targetType="String") List<String> ride_route;
  private final @ModelField(targetType="RidersJoin") @HasMany(associatedWith = "ride", type = RidersJoin.class) List<RidersJoin> riders = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
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
  
  public List<String> getRideRoute() {
      return ride_route;
  }
  
  public List<RidersJoin> getRiders() {
      return riders;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Ride(String id, String owner_id, String ride_departure_time, Integer available_seats, List<String> ride_route) {
    this.id = id;
    this.owner_id = owner_id;
    this.ride_departure_time = ride_departure_time;
    this.available_seats = available_seats;
    this.ride_route = ride_route;
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
              ObjectsCompat.equals(getOwnerId(), ride.getOwnerId()) &&
              ObjectsCompat.equals(getRideDepartureTime(), ride.getRideDepartureTime()) &&
              ObjectsCompat.equals(getAvailableSeats(), ride.getAvailableSeats()) &&
              ObjectsCompat.equals(getRideRoute(), ride.getRideRoute()) &&
              ObjectsCompat.equals(getCreatedAt(), ride.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), ride.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getOwnerId())
      .append(getRideDepartureTime())
      .append(getAvailableSeats())
      .append(getRideRoute())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Ride {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("owner_id=" + String.valueOf(getOwnerId()) + ", ")
      .append("ride_departure_time=" + String.valueOf(getRideDepartureTime()) + ", ")
      .append("available_seats=" + String.valueOf(getAvailableSeats()) + ", ")
      .append("ride_route=" + String.valueOf(getRideRoute()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static OwnerIdStep builder() {
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      owner_id,
      ride_departure_time,
      available_seats,
      ride_route);
  }
  public interface OwnerIdStep {
    RideDepartureTimeStep ownerId(String ownerId);
  }
  

  public interface RideDepartureTimeStep {
    AvailableSeatsStep rideDepartureTime(String rideDepartureTime);
  }
  

  public interface AvailableSeatsStep {
    BuildStep availableSeats(Integer availableSeats);
  }
  

  public interface BuildStep {
    Ride build();
    BuildStep id(String id);
    BuildStep rideRoute(List<String> rideRoute);
  }
  

  public static class Builder implements OwnerIdStep, RideDepartureTimeStep, AvailableSeatsStep, BuildStep {
    private String id;
    private String owner_id;
    private String ride_departure_time;
    private Integer available_seats;
    private List<String> ride_route;
    @Override
     public Ride build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Ride(
          id,
          owner_id,
          ride_departure_time,
          available_seats,
          ride_route);
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
     public BuildStep availableSeats(Integer availableSeats) {
        Objects.requireNonNull(availableSeats);
        this.available_seats = availableSeats;
        return this;
    }
    
    @Override
     public BuildStep rideRoute(List<String> rideRoute) {
        this.ride_route = rideRoute;
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
    private CopyOfBuilder(String id, String ownerId, String rideDepartureTime, Integer availableSeats, List<String> rideRoute) {
      super.id(id);
      super.ownerId(ownerId)
        .rideDepartureTime(rideDepartureTime)
        .availableSeats(availableSeats)
        .rideRoute(rideRoute);
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
     public CopyOfBuilder rideRoute(List<String> rideRoute) {
      return (CopyOfBuilder) super.rideRoute(rideRoute);
    }
  }
  
}
