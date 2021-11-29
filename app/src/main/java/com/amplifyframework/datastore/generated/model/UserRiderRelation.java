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

/** This is an auto generated class representing the UserRiderRelation type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserRiderRelations")
public final class UserRiderRelation implements Model {
  public static final QueryField ID = field("UserRiderRelation", "id");
  public static final QueryField USER_ID = field("UserRiderRelation", "userID");
  public static final QueryField RIDE_ID = field("UserRiderRelation", "rideID");
  public static final QueryField USER = field("UserRiderRelation", "userID");
  public static final QueryField RIDE = field("UserRiderRelation", "rideID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String userID;
  private final @ModelField(targetType="ID", isRequired = true) String rideID;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "userID", type = User.class) User user;
  private final @ModelField(targetType="Ride", isRequired = true) @BelongsTo(targetName = "rideID", type = Ride.class) Ride ride;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userID;
  }
  
  public String getRideId() {
      return rideID;
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
  
  private UserRiderRelation(String id, String userID, String rideID, User user, Ride ride) {
    this.id = id;
    this.userID = userID;
    this.rideID = rideID;
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
      UserRiderRelation userRiderRelation = (UserRiderRelation) obj;
      return ObjectsCompat.equals(getId(), userRiderRelation.getId()) &&
              ObjectsCompat.equals(getUserId(), userRiderRelation.getUserId()) &&
              ObjectsCompat.equals(getRideId(), userRiderRelation.getRideId()) &&
              ObjectsCompat.equals(getUser(), userRiderRelation.getUser()) &&
              ObjectsCompat.equals(getRide(), userRiderRelation.getRide()) &&
              ObjectsCompat.equals(getCreatedAt(), userRiderRelation.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userRiderRelation.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getRideId())
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
      .append("UserRiderRelation {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userID=" + String.valueOf(getUserId()) + ", ")
      .append("rideID=" + String.valueOf(getRideId()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("ride=" + String.valueOf(getRide()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UserIdStep builder() {
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
  public static UserRiderRelation justId(String id) {
    return new UserRiderRelation(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userID,
      rideID,
      user,
      ride);
  }
  public interface UserIdStep {
    RideIdStep userId(String userId);
  }
  

  public interface RideIdStep {
    UserStep rideId(String rideId);
  }
  

  public interface UserStep {
    RideStep user(User user);
  }
  

  public interface RideStep {
    BuildStep ride(Ride ride);
  }
  

  public interface BuildStep {
    UserRiderRelation build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UserIdStep, RideIdStep, UserStep, RideStep, BuildStep {
    private String id;
    private String userID;
    private String rideID;
    private User user;
    private Ride ride;
    @Override
     public UserRiderRelation build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserRiderRelation(
          id,
          userID,
          rideID,
          user,
          ride);
    }
    
    @Override
     public RideIdStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userID = userId;
        return this;
    }
    
    @Override
     public UserStep rideId(String rideId) {
        Objects.requireNonNull(rideId);
        this.rideID = rideId;
        return this;
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
    private CopyOfBuilder(String id, String userId, String rideId, User user, Ride ride) {
      super.id(id);
      super.userId(userId)
        .rideId(rideId)
        .user(user)
        .ride(ride);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder rideId(String rideId) {
      return (CopyOfBuilder) super.rideId(rideId);
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
