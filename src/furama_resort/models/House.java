package furama_resort.models;

public class House extends Facility{
    private String roomStandard;    // tiêu chuẩn phòng
    private int numberOfFloors;     //số tầng

    public House() {
    }

    public House(String roomStandard, int numberOfFloors) {
        this.roomStandard = roomStandard;
        this.numberOfFloors = numberOfFloors;
    }

    public House(String codeService,String nameService, Double useArea, Double rentalCost, int maxPerson, String typeOfRental, String roomStandard, int numberOfFloors) {
        super(codeService,nameService, useArea, rentalCost, maxPerson, typeOfRental);
        this.roomStandard = roomStandard;
        this.numberOfFloors = numberOfFloors;
    }

    public String getRoomStandard() {
        return roomStandard;
    }

    public void setRoomStandard(String roomStandard) {
        this.roomStandard = roomStandard;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    @Override
    public String toString() {
        return "{" + super.toString()+
                "roomStandard='" + roomStandard + '\'' +
                ", numberOfFloors=" + numberOfFloors +
                '}';
    }

    public String getInfo() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s",getCodeService(),getNameService(),getUseArea(),getRentalCost(),getMaxPerson(),getTypeOfRental(),getRoomStandard(),getNumberOfFloors());
    }
}
