package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
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

/** This is an auto generated class representing the RidersJoin type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "RidersJoins")
public final class RidersJoin implements Model {
  public static final QueryField ID = field("RidersJoin", "id");
  public static final QueryField USER = field("RidersJoin", "ridersJoinUserId");
  public static final QueryField RIDE = field("RidersJoin", "ridersJoinRideId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "ridersJoinUserId", type = User.class) User user;
  private final @ModelField(targetType="Ride", isRequired = true) @BelongsTo(targetName = "ridersJoinRideId", type = Ride.class) Ride ride;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public User getUser() {
      return user;
  }
  
  public Ride getRide() {
      return ride;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private RidersJoin(String id, User user, Ride ride) {
    this.id = id;
    this.user = user;
    this.ride = ride;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      RidersJoin ridersJoin = (RidersJoin) obj;
      return ObjectsCompat.equals(getId(), ridersJoin.getId()) &&
              ObjectsCompat.equals(getUser(), ridersJoin.getUser()) &&
              ObjectsCompat.equals(getRide(), ridersJoin.getRide()) &&
              ObjectsCompat.equals(getCreatedAt(), ridersJoin.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), ridersJoin.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUser())
      .append(getRide())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("RidersJoin {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("ride=" + String.valueOf(getRide()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UserStep builder() {
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
  public static RidersJoin justId(String id) {
    return new RidersJoin(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      user,
      ride);
  }
  public interface UserStep {
    RideStep user(User user);
  }
  

  public interface RideStep {
    BuildStep ride(Ride ride);
  }
  

  public interface BuildStep {
    RidersJoin build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UserStep, RideStep, BuildStep {
    private String id;
    private User user;
    private Ride ride;
    @Override
     public RidersJoin build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new RidersJoin(
          id,
          user,
          ride);
    }
    
    @Override
     public RideStep user(User user) {
        Objects.requireNonNull(user);
        this.user = user;
        return this;
    }
    
    @Override
     public BuildStep ride(Ride ride) {
        Objects.requireNonNull(ride);
        this.ride = ride;
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
    private CopyOfBuilder(String id, User user, Ride ride) {
      super.id(id);
      super.user(user)
        .ride(ride);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder ride(Ride ride) {
      return (CopyOfBuilder) super.ride(ride);
    }
  }
  
}
