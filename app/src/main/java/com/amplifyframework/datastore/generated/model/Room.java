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

/** This is an auto generated class representing the Room type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Rooms")
public final class Room implements Model {
  public static final QueryField ID = field("Room", "id");
  public static final QueryField ROOM_OWNER_ID = field("Room", "room_owner_id");
  public static final QueryField ROOM_OWNER_EMAIL = field("Room", "room_owner_email");
  public static final QueryField ROOM_OWNER_USER_NAME = field("Room", "room_owner_user_name");
  public static final QueryField ROOM_DESCRIPTION = field("Room", "room_description");
  public static final QueryField ROOM_LOCATION = field("Room", "room_location");
  public static final QueryField ROOM_IMAGE = field("Room", "room_image");
  public static final QueryField ROOM_COST = field("Room", "room_cost");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String room_owner_id;
  private final @ModelField(targetType="String", isRequired = true) String room_owner_email;
  private final @ModelField(targetType="String", isRequired = true) String room_owner_user_name;
  private final @ModelField(targetType="String") String room_description;
  private final @ModelField(targetType="String", isRequired = true) String room_location;
  private final @ModelField(targetType="String", isRequired = true) String room_image;
  private final @ModelField(targetType="Float", isRequired = true) Double room_cost;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getRoomOwnerId() {
      return room_owner_id;
  }
  
  public String getRoomOwnerEmail() {
      return room_owner_email;
  }
  
  public String getRoomOwnerUserName() {
      return room_owner_user_name;
  }
  
  public String getRoomDescription() {
      return room_description;
  }
  
  public String getRoomLocation() {
      return room_location;
  }
  
  public String getRoomImage() {
      return room_image;
  }
  
  public Double getRoomCost() {
      return room_cost;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Room(String id, String room_owner_id, String room_owner_email, String room_owner_user_name, String room_description, String room_location, String room_image, Double room_cost) {
    this.id = id;
    this.room_owner_id = room_owner_id;
    this.room_owner_email = room_owner_email;
    this.room_owner_user_name = room_owner_user_name;
    this.room_description = room_description;
    this.room_location = room_location;
    this.room_image = room_image;
    this.room_cost = room_cost;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Room room = (Room) obj;
      return ObjectsCompat.equals(getId(), room.getId()) &&
              ObjectsCompat.equals(getRoomOwnerId(), room.getRoomOwnerId()) &&
              ObjectsCompat.equals(getRoomOwnerEmail(), room.getRoomOwnerEmail()) &&
              ObjectsCompat.equals(getRoomOwnerUserName(), room.getRoomOwnerUserName()) &&
              ObjectsCompat.equals(getRoomDescription(), room.getRoomDescription()) &&
              ObjectsCompat.equals(getRoomLocation(), room.getRoomLocation()) &&
              ObjectsCompat.equals(getRoomImage(), room.getRoomImage()) &&
              ObjectsCompat.equals(getRoomCost(), room.getRoomCost()) &&
              ObjectsCompat.equals(getCreatedAt(), room.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), room.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getRoomOwnerId())
      .append(getRoomOwnerEmail())
      .append(getRoomOwnerUserName())
      .append(getRoomDescription())
      .append(getRoomLocation())
      .append(getRoomImage())
      .append(getRoomCost())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Room {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("room_owner_id=" + String.valueOf(getRoomOwnerId()) + ", ")
      .append("room_owner_email=" + String.valueOf(getRoomOwnerEmail()) + ", ")
      .append("room_owner_user_name=" + String.valueOf(getRoomOwnerUserName()) + ", ")
      .append("room_description=" + String.valueOf(getRoomDescription()) + ", ")
      .append("room_location=" + String.valueOf(getRoomLocation()) + ", ")
      .append("room_image=" + String.valueOf(getRoomImage()) + ", ")
      .append("room_cost=" + String.valueOf(getRoomCost()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static RoomOwnerIdStep builder() {
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
  public static Room justId(String id) {
    return new Room(
      id,
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
      room_owner_id,
      room_owner_email,
      room_owner_user_name,
      room_description,
      room_location,
      room_image,
      room_cost);
  }
  public interface RoomOwnerIdStep {
    RoomOwnerEmailStep roomOwnerId(String roomOwnerId);
  }
  

  public interface RoomOwnerEmailStep {
    RoomOwnerUserNameStep roomOwnerEmail(String roomOwnerEmail);
  }
  

  public interface RoomOwnerUserNameStep {
    RoomLocationStep roomOwnerUserName(String roomOwnerUserName);
  }
  

  public interface RoomLocationStep {
    RoomImageStep roomLocation(String roomLocation);
  }
  

  public interface RoomImageStep {
    RoomCostStep roomImage(String roomImage);
  }
  

  public interface RoomCostStep {
    BuildStep roomCost(Double roomCost);
  }
  

  public interface BuildStep {
    Room build();
    BuildStep id(String id);
    BuildStep roomDescription(String roomDescription);
  }
  

  public static class Builder implements RoomOwnerIdStep, RoomOwnerEmailStep, RoomOwnerUserNameStep, RoomLocationStep, RoomImageStep, RoomCostStep, BuildStep {
    private String id;
    private String room_owner_id;
    private String room_owner_email;
    private String room_owner_user_name;
    private String room_location;
    private String room_image;
    private Double room_cost;
    private String room_description;
    @Override
     public Room build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Room(
          id,
          room_owner_id,
          room_owner_email,
          room_owner_user_name,
          room_description,
          room_location,
          room_image,
          room_cost);
    }
    
    @Override
     public RoomOwnerEmailStep roomOwnerId(String roomOwnerId) {
        Objects.requireNonNull(roomOwnerId);
        this.room_owner_id = roomOwnerId;
        return this;
    }
    
    @Override
     public RoomOwnerUserNameStep roomOwnerEmail(String roomOwnerEmail) {
        Objects.requireNonNull(roomOwnerEmail);
        this.room_owner_email = roomOwnerEmail;
        return this;
    }
    
    @Override
     public RoomLocationStep roomOwnerUserName(String roomOwnerUserName) {
        Objects.requireNonNull(roomOwnerUserName);
        this.room_owner_user_name = roomOwnerUserName;
        return this;
    }
    
    @Override
     public RoomImageStep roomLocation(String roomLocation) {
        Objects.requireNonNull(roomLocation);
        this.room_location = roomLocation;
        return this;
    }
    
    @Override
     public RoomCostStep roomImage(String roomImage) {
        Objects.requireNonNull(roomImage);
        this.room_image = roomImage;
        return this;
    }
    
    @Override
     public BuildStep roomCost(Double roomCost) {
        Objects.requireNonNull(roomCost);
        this.room_cost = roomCost;
        return this;
    }
    
    @Override
     public BuildStep roomDescription(String roomDescription) {
        this.room_description = roomDescription;
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
    private CopyOfBuilder(String id, String roomOwnerId, String roomOwnerEmail, String roomOwnerUserName, String roomDescription, String roomLocation, String roomImage, Double roomCost) {
      super.id(id);
      super.roomOwnerId(roomOwnerId)
        .roomOwnerEmail(roomOwnerEmail)
        .roomOwnerUserName(roomOwnerUserName)
        .roomLocation(roomLocation)
        .roomImage(roomImage)
        .roomCost(roomCost)
        .roomDescription(roomDescription);
    }
    
    @Override
     public CopyOfBuilder roomOwnerId(String roomOwnerId) {
      return (CopyOfBuilder) super.roomOwnerId(roomOwnerId);
    }
    
    @Override
     public CopyOfBuilder roomOwnerEmail(String roomOwnerEmail) {
      return (CopyOfBuilder) super.roomOwnerEmail(roomOwnerEmail);
    }
    
    @Override
     public CopyOfBuilder roomOwnerUserName(String roomOwnerUserName) {
      return (CopyOfBuilder) super.roomOwnerUserName(roomOwnerUserName);
    }
    
    @Override
     public CopyOfBuilder roomLocation(String roomLocation) {
      return (CopyOfBuilder) super.roomLocation(roomLocation);
    }
    
    @Override
     public CopyOfBuilder roomImage(String roomImage) {
      return (CopyOfBuilder) super.roomImage(roomImage);
    }
    
    @Override
     public CopyOfBuilder roomCost(Double roomCost) {
      return (CopyOfBuilder) super.roomCost(roomCost);
    }
    
    @Override
     public CopyOfBuilder roomDescription(String roomDescription) {
      return (CopyOfBuilder) super.roomDescription(roomDescription);
    }
  }
  
}
