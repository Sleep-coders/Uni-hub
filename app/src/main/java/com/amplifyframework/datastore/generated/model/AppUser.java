package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasOne;
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

/** This is an auto generated class representing the AppUser type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "AppUsers")
@Index(name = "undefined", fields = {"id","user_email"})
@Index(name = "byEmail", fields = {"user_email","id"})
public final class AppUser implements Model {
  public static final QueryField ID = field("AppUser", "id");
  public static final QueryField USER_REAL_NAME = field("AppUser", "user_real_name");
  public static final QueryField USER_NICKNAME = field("AppUser", "user_nickname");
  public static final QueryField USER_PHONE_NUMBER = field("AppUser", "user_phone_number");
  public static final QueryField USER_EMAIL = field("AppUser", "user_email");
  public static final QueryField USER_LOCATION = field("AppUser", "user_location");
  public static final QueryField USER_UNIVERSITY = field("AppUser", "user_university");
  public static final QueryField USER_IMG = field("AppUser", "user_img");
  public static final QueryField APP_USER_CAR_ID = field("AppUser", "appUserCarId");
  public static final QueryField APP_USER_ROOM_ID = field("AppUser", "appUserRoomId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String user_real_name;
  private final @ModelField(targetType="String", isRequired = true) String user_nickname;
  private final @ModelField(targetType="String", isRequired = true) String user_phone_number;
  private final @ModelField(targetType="String", isRequired = true) String user_email;
  private final @ModelField(targetType="String") String user_location;
  private final @ModelField(targetType="String") String user_university;
  private final @ModelField(targetType="String") String user_img;
  private final @ModelField(targetType="Car") @HasOne(associatedWith = "id", type = Car.class) Car car = null;
  private final @ModelField(targetType="Ride") @HasMany(associatedWith = "appUserRidesId", type = Ride.class) List<Ride> rides = null;
  private final @ModelField(targetType="Room") @HasOne(associatedWith = "id", type = Room.class) Room room = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String appUserCarId;
  private final @ModelField(targetType="ID") String appUserRoomId;
  public String getId() {
      return id;
  }
  
  public String getUserRealName() {
      return user_real_name;
  }
  
  public String getUserNickname() {
      return user_nickname;
  }
  
  public String getUserPhoneNumber() {
      return user_phone_number;
  }
  
  public String getUserEmail() {
      return user_email;
  }
  
  public String getUserLocation() {
      return user_location;
  }
  
  public String getUserUniversity() {
      return user_university;
  }
  
  public String getUserImg() {
      return user_img;
  }
  
  public Car getCar() {
      return car;
  }
  
  public List<Ride> getRides() {
      return rides;
  }
  
  public Room getRoom() {
      return room;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getAppUserCarId() {
      return appUserCarId;
  }
  
  public String getAppUserRoomId() {
      return appUserRoomId;
  }
  
  private AppUser(String id, String user_real_name, String user_nickname, String user_phone_number, String user_email, String user_location, String user_university, String user_img, String appUserCarId, String appUserRoomId) {
    this.id = id;
    this.user_real_name = user_real_name;
    this.user_nickname = user_nickname;
    this.user_phone_number = user_phone_number;
    this.user_email = user_email;
    this.user_location = user_location;
    this.user_university = user_university;
    this.user_img = user_img;
    this.appUserCarId = appUserCarId;
    this.appUserRoomId = appUserRoomId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      AppUser appUser = (AppUser) obj;
      return ObjectsCompat.equals(getId(), appUser.getId()) &&
              ObjectsCompat.equals(getUserRealName(), appUser.getUserRealName()) &&
              ObjectsCompat.equals(getUserNickname(), appUser.getUserNickname()) &&
              ObjectsCompat.equals(getUserPhoneNumber(), appUser.getUserPhoneNumber()) &&
              ObjectsCompat.equals(getUserEmail(), appUser.getUserEmail()) &&
              ObjectsCompat.equals(getUserLocation(), appUser.getUserLocation()) &&
              ObjectsCompat.equals(getUserUniversity(), appUser.getUserUniversity()) &&
              ObjectsCompat.equals(getUserImg(), appUser.getUserImg()) &&
              ObjectsCompat.equals(getCreatedAt(), appUser.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), appUser.getUpdatedAt()) &&
              ObjectsCompat.equals(getAppUserCarId(), appUser.getAppUserCarId()) &&
              ObjectsCompat.equals(getAppUserRoomId(), appUser.getAppUserRoomId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserRealName())
      .append(getUserNickname())
      .append(getUserPhoneNumber())
      .append(getUserEmail())
      .append(getUserLocation())
      .append(getUserUniversity())
      .append(getUserImg())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getAppUserCarId())
      .append(getAppUserRoomId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("AppUser {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user_real_name=" + String.valueOf(getUserRealName()) + ", ")
      .append("user_nickname=" + String.valueOf(getUserNickname()) + ", ")
      .append("user_phone_number=" + String.valueOf(getUserPhoneNumber()) + ", ")
      .append("user_email=" + String.valueOf(getUserEmail()) + ", ")
      .append("user_location=" + String.valueOf(getUserLocation()) + ", ")
      .append("user_university=" + String.valueOf(getUserUniversity()) + ", ")
      .append("user_img=" + String.valueOf(getUserImg()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("appUserCarId=" + String.valueOf(getAppUserCarId()) + ", ")
      .append("appUserRoomId=" + String.valueOf(getAppUserRoomId()))
      .append("}")
      .toString();
  }
  
  public static UserRealNameStep builder() {
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
  public static AppUser justId(String id) {
    return new AppUser(
      id,
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
      user_real_name,
      user_nickname,
      user_phone_number,
      user_email,
      user_location,
      user_university,
      user_img,
      appUserCarId,
      appUserRoomId);
  }
  public interface UserRealNameStep {
    UserNicknameStep userRealName(String userRealName);
  }
  

  public interface UserNicknameStep {
    UserPhoneNumberStep userNickname(String userNickname);
  }
  

  public interface UserPhoneNumberStep {
    UserEmailStep userPhoneNumber(String userPhoneNumber);
  }
  

  public interface UserEmailStep {
    BuildStep userEmail(String userEmail);
  }
  

  public interface BuildStep {
    AppUser build();
    BuildStep id(String id);
    BuildStep userLocation(String userLocation);
    BuildStep userUniversity(String userUniversity);
    BuildStep userImg(String userImg);
    BuildStep appUserCarId(String appUserCarId);
    BuildStep appUserRoomId(String appUserRoomId);
  }
  

  public static class Builder implements UserRealNameStep, UserNicknameStep, UserPhoneNumberStep, UserEmailStep, BuildStep {
    private String id;
    private String user_real_name;
    private String user_nickname;
    private String user_phone_number;
    private String user_email;
    private String user_location;
    private String user_university;
    private String user_img;
    private String appUserCarId;
    private String appUserRoomId;
    @Override
     public AppUser build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new AppUser(
          id,
          user_real_name,
          user_nickname,
          user_phone_number,
          user_email,
          user_location,
          user_university,
          user_img,
          appUserCarId,
          appUserRoomId);
    }
    
    @Override
     public UserNicknameStep userRealName(String userRealName) {
        Objects.requireNonNull(userRealName);
        this.user_real_name = userRealName;
        return this;
    }
    
    @Override
     public UserPhoneNumberStep userNickname(String userNickname) {
        Objects.requireNonNull(userNickname);
        this.user_nickname = userNickname;
        return this;
    }
    
    @Override
     public UserEmailStep userPhoneNumber(String userPhoneNumber) {
        Objects.requireNonNull(userPhoneNumber);
        this.user_phone_number = userPhoneNumber;
        return this;
    }
    
    @Override
     public BuildStep userEmail(String userEmail) {
        Objects.requireNonNull(userEmail);
        this.user_email = userEmail;
        return this;
    }
    
    @Override
     public BuildStep userLocation(String userLocation) {
        this.user_location = userLocation;
        return this;
    }
    
    @Override
     public BuildStep userUniversity(String userUniversity) {
        this.user_university = userUniversity;
        return this;
    }
    
    @Override
     public BuildStep userImg(String userImg) {
        this.user_img = userImg;
        return this;
    }
    
    @Override
     public BuildStep appUserCarId(String appUserCarId) {
        this.appUserCarId = appUserCarId;
        return this;
    }
    
    @Override
     public BuildStep appUserRoomId(String appUserRoomId) {
        this.appUserRoomId = appUserRoomId;
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
    private CopyOfBuilder(String id, String userRealName, String userNickname, String userPhoneNumber, String userEmail, String userLocation, String userUniversity, String userImg, String appUserCarId, String appUserRoomId) {
      super.id(id);
      super.userRealName(userRealName)
        .userNickname(userNickname)
        .userPhoneNumber(userPhoneNumber)
        .userEmail(userEmail)
        .userLocation(userLocation)
        .userUniversity(userUniversity)
        .userImg(userImg)
        .appUserCarId(appUserCarId)
        .appUserRoomId(appUserRoomId);
    }
    
    @Override
     public CopyOfBuilder userRealName(String userRealName) {
      return (CopyOfBuilder) super.userRealName(userRealName);
    }
    
    @Override
     public CopyOfBuilder userNickname(String userNickname) {
      return (CopyOfBuilder) super.userNickname(userNickname);
    }
    
    @Override
     public CopyOfBuilder userPhoneNumber(String userPhoneNumber) {
      return (CopyOfBuilder) super.userPhoneNumber(userPhoneNumber);
    }
    
    @Override
     public CopyOfBuilder userEmail(String userEmail) {
      return (CopyOfBuilder) super.userEmail(userEmail);
    }
    
    @Override
     public CopyOfBuilder userLocation(String userLocation) {
      return (CopyOfBuilder) super.userLocation(userLocation);
    }
    
    @Override
     public CopyOfBuilder userUniversity(String userUniversity) {
      return (CopyOfBuilder) super.userUniversity(userUniversity);
    }
    
    @Override
     public CopyOfBuilder userImg(String userImg) {
      return (CopyOfBuilder) super.userImg(userImg);
    }
    
    @Override
     public CopyOfBuilder appUserCarId(String appUserCarId) {
      return (CopyOfBuilder) super.appUserCarId(appUserCarId);
    }
    
    @Override
     public CopyOfBuilder appUserRoomId(String appUserRoomId) {
      return (CopyOfBuilder) super.appUserRoomId(appUserRoomId);
    }
  }
  
}
