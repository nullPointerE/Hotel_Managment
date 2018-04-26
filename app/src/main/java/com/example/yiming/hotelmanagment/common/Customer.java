package com.example.yiming.hotelmanagment.common;
import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable{
    private String title, firstName,middleName, lastName,emailAddress, gender, companyName, address,creditCard;
    private String city, postalCode, country, daytimePhone,mobilePhone,comments, purposeOfVisit;
    boolean createNewAccount, isTermsAccepted;


    int CustomerId;
    int numberOfCustomers;
    int assignedRoom;
    int roomIsGuaranteed;


    public Customer() {

    }

    protected Customer(Parcel in) {
        title = in.readString();
        firstName = in.readString();
        middleName = in.readString();
        lastName = in.readString();
        emailAddress = in.readString();
        gender = in.readString();
        companyName = in.readString();
        address = in.readString();
        creditCard = in.readString();
        city = in.readString();
        postalCode = in.readString();
        country = in.readString();
        daytimePhone = in.readString();
        mobilePhone = in.readString();
        comments = in.readString();
        purposeOfVisit = in.readString();
        createNewAccount = in.readByte() != 0;
        isTermsAccepted = in.readByte() != 0;
        CustomerId = in.readInt();
        numberOfCustomers = in.readInt();
        assignedRoom = in.readInt();
        roomIsGuaranteed = in.readInt();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    public int getAssignedRoom() {
        return assignedRoom;
    }

    public void setAssignedRoom(int assignedRoom) {
        this.assignedRoom = assignedRoom;
    }
    public int isRoomIsGuaranteed() {
        return roomIsGuaranteed;
    }

    public void setRoomIsGuaranteed(int roomIsGuaranteed) {
        this.roomIsGuaranteed = roomIsGuaranteed;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDaytimePhone() {
        return daytimePhone;
    }

    public void setDaytimePhone(String daytimePhone) {
        this.daytimePhone = daytimePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }


    public boolean isCreateNewAccount() {
        return createNewAccount;
    }

    public void setCreateNewAccount(boolean createNewAccount) {
        this.createNewAccount = createNewAccount;
    }

    public boolean isTermsAccepted() {
        return isTermsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        isTermsAccepted = termsAccepted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(firstName);
        parcel.writeString(middleName);
        parcel.writeString(lastName);
        parcel.writeString(emailAddress);
        parcel.writeString(gender);
        parcel.writeString(companyName);
        parcel.writeString(address);
        parcel.writeString(creditCard);
        parcel.writeString(city);
        parcel.writeString(postalCode);
        parcel.writeString(country);
        parcel.writeString(daytimePhone);
        parcel.writeString(mobilePhone);
        parcel.writeString(comments);
        parcel.writeString(purposeOfVisit);
        parcel.writeByte((byte) (createNewAccount ? 1 : 0));
        parcel.writeByte((byte) (isTermsAccepted ? 1 : 0));
        parcel.writeInt(CustomerId);
        parcel.writeInt(numberOfCustomers);
        parcel.writeInt(assignedRoom);
        parcel.writeInt(roomIsGuaranteed);

    }
}