package furama_resort.services.impl;

import furama_resort.controllers.FuramaController;
import furama_resort.models.*;
import furama_resort.services.IFacilityService;
import furama_resort.utils.exception.CheckExceptionsUtils;
import furama_resort.utils.exception.FacilityExceptionUtils;

import java.io.*;
import java.util.*;

public class FacilityService implements IFacilityService {
    public static final String VILLA_FILE = "src\\furama_resort\\data\\villa.csv";
    public static final String HOUSE_FILE = "src\\furama_resort\\data\\house.csv";
    public static final String ROOM_FILE = "src\\furama_resort\\data\\room.csv";
    private static Scanner scanner = new Scanner(System.in);
    private static List<Facility> maintenanceList = new ArrayList<>();
    private static Map<Facility, Integer> facilityList = new LinkedHashMap<>();
    private static LinkedHashMap<Villa, Integer> villaList = new LinkedHashMap<>();
    private static LinkedHashMap<House, Integer> houseList = new LinkedHashMap<>();
    private static LinkedHashMap<Room, Integer> roomList = new LinkedHashMap<>();

    public static Map<Facility, Integer> setInfo() throws IOException {
        villaList = readVilla();
        houseList = readHouse();
        roomList = readRoom();
        for (Villa villa : villaList.keySet()) {
            facilityList.put(villa, 0);
        }
        for (House house : houseList.keySet()) {
            facilityList.put(house, houseList.get(house));
        }
        for (Room room : roomList.keySet()) {
            facilityList.put(room, 0);
        }
        return facilityList;

    }

    public static void setData() throws IOException {
        List<Booking> bookingList = BookingService.readBooking();
        for (int i = 0; i < bookingList.size(); i++) {
            for (Facility key : facilityList.keySet()) {
                if (bookingList.get(i).getServiceCode().equals(key.getCodeService())) {
                    facilityList.replace(key, facilityList.get(key) + 1);
                    if (facilityList.get(key) == 5) {
                        maintenanceList.add(key);
                        writeMaintenace(maintenanceList);
                    }
                }
            }
        }

    }


    @Override
    public void displayList() throws IOException {
        villaList = readVilla();
        houseList = readHouse();
        roomList = readRoom();
        for (Villa villa : villaList.keySet()) {
            System.out.println(villa);
        }
        for (House house : houseList.keySet()) {
            System.out.println(house);
        }
        for (Room room : roomList.keySet()) {
            System.out.println(room);
        }
    }

    @Override
    public void addNew() {
        int choice;
        while (true) {
            try {
                System.out.println("M???i b???n nh???p l???a ch???n: ");
                System.out.println("1. Th??m m???i Villa");
                System.out.println("2. Th??m m???i House");
                System.out.println("3. Th??m m???i Room");
                System.out.println("4. Tr??? v??? menu");

                System.out.print("L???a ch???n c???a b???n l??: ");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        villaList = readVilla();
                        Villa villa = this.getInfoVilla();
                        villaList.put(villa, 0);
                        writeVillaFile(villaList);
                        break;
                    case 2:
                        houseList = readHouse();
                        House house = this.getInfoHouse();
                        houseList.put(house, 0);
                        writeHouseFile(houseList);
                        break;
                    case 3:
                        roomList = readRoom();
                        Room room = this.getInfoRoom();
                        roomList.put(room, 0);
                        writeRoomFile(roomList);
                        break;
                    case 4:
                        FuramaController.facilityManagement();
                        break;
                    default:
                        System.out.println("S??? nh???p v??o kh??ng kh??? thi!");
                }

                CheckExceptionsUtils.addNewCheck(choice);
            } catch (CheckExceptionsUtils | NumberFormatException | IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    public void edit() {

    }

    @Override
    public void displayListFacilityMaintenance() throws IOException {
        setInfo();
        setData();
        maintenanceList = readMaintenanceList();
        for (Facility facility : maintenanceList) {
            System.out.println(facility.toString());
        }

    }

    private Villa getInfoVilla() {
        String codeService = addVillaCode();
        String nameService = addVillaName();
        Double areaUse = addareaUseVilla();
        Double rentalCost = addRentalCostVilla();
        int maxPerson = addMaxPersonVilla();
        String typeOfRental = addtypeOfRentalVilla();
        String roomStandard = addRoomStandardVilla();
        Double swimmingPoolAarea = addswimmingPoolAarea();
        int numberOfFloors = addNumberOfFloorsVilla();
        Villa villa = new Villa(codeService, nameService, areaUse, rentalCost, maxPerson, typeOfRental, roomStandard, swimmingPoolAarea, numberOfFloors);
        return villa;
    }

    private String addVillaCode() {
        String code;
        while (true) {
            try {
                System.out.print("Nh???p m?? d???ch v??? c???a Villa: ");
                code = scanner.nextLine();
                if (FacilityExceptionUtils.codeVillaCheck(code)) {
                    return code;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String addVillaName() {
        String name;
        while (true) {
            try {
                System.out.print("Nh???p t??n d???ch v??? c???a Villa - T??n d???ch v??? ph???i vi???t hoa k?? t??? ?????u, c??c k?? t??? sau l?? k?? t??? b??nh th?????ng: ");
                name = scanner.nextLine();
                if (FacilityExceptionUtils.nameServiceCheck(name)) {
                    return name;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Double addareaUseVilla() {
        Double araeUse;
        while (true) {
            try {
                System.out.print("Nh???p di???n t??ch s??? d???ng c???a Villa - Di???n t??ch s??? d???ng ph???i l?? s??? th???c l???n h??n 30m2: ");
                araeUse = Double.parseDouble(scanner.nextLine());
                if (FacilityExceptionUtils.areaUseCheck(araeUse)) {
                    return araeUse;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Double addRentalCostVilla() {
        Double rentalCost;
        while (true) {
            try {
                System.out.print("Nh???p chi ph?? thu?? c???a Villa - Chi ph?? thu?? ph???i l?? s??? d????ng: ");
                rentalCost = Double.parseDouble(scanner.nextLine());
                if (FacilityExceptionUtils.rentalCoatCheck(rentalCost)) {
                    return rentalCost;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int addMaxPersonVilla() {
        int maxPerson;
        while (true) {
            try {
                System.out.print("S??? l?????ng ng?????i t???i ??a c???a Villa - S??? l?????ng ng?????i t???i ??a ph???i ph???i l???n h??n 0 v?? nh??? h??n 20: ");
                maxPerson = Integer.parseInt(scanner.nextLine());
                if (FacilityExceptionUtils.maxPersonCheck(maxPerson)) {
                    return maxPerson;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String addtypeOfRentalVilla() {
        String typeOfRental;
        while (true) {
            try {
                System.out.print("Nh???p ki???u thu?? c???a Villa: ");
                typeOfRental = scanner.nextLine();
                if (FacilityExceptionUtils.typeOfRentalCheck(typeOfRental)) {
                    return typeOfRental;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String addRoomStandardVilla() {
        String roomStandard;
        while (true) {
            try {
                System.out.print("Nh???p ti??u chu???n ph??ng c???a Villa: ");
                roomStandard = scanner.nextLine();
                if (FacilityExceptionUtils.roomStandardCheck(roomStandard)) {
                    return roomStandard;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Double addswimmingPoolAarea() {
        Double swimmingPoolAarea;
        while (true) {
            try {
                System.out.print("Nh???p di???n t??ch h??? b??i c???a Villa - di???n t??ch h??? b??i ph???i l?? s??? th???c l???n h??n 30m2: ");
                swimmingPoolAarea = Double.parseDouble(scanner.nextLine());
                if (FacilityExceptionUtils.swimmingPoolAareaCheck(swimmingPoolAarea)) {
                    return swimmingPoolAarea;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int addNumberOfFloorsVilla() {
        int numberOfFloors;
        while (true) {
            try {
                System.out.print("Nh???p s??? t???ng c???a Villa: ");
                numberOfFloors = Integer.parseInt(scanner.nextLine());
                if (FacilityExceptionUtils.numberOfFloorsCheck(numberOfFloors)) {
                    return numberOfFloors;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

//    --------------------------------------

    private House getInfoHouse() {
        String codeService = addHouseCode();
        String nameService = addHouseName();
        Double areaUse = addareaUse();
        Double rentalCost = addRentalCost();
        int maxPerson = addMaxPerson();
        String typeOfRental = addtypeOfRental();
        String roomStandard = addRoomStandard();
        int numberOfFloors = addNumberOfFloors();

        House house = new House(codeService, nameService, areaUse, rentalCost, maxPerson, typeOfRental, roomStandard, numberOfFloors);
        return house;
    }

    private String addHouseCode() {
        String code;
        while (true) {
            try {
                System.out.print("Nh???p m?? d???ch v??? c???a House: ");
                code = scanner.nextLine();
                if (FacilityExceptionUtils.codeHouseCheck(code)) {
                    return code;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String addHouseName() {
        String name;
        while (true) {
            try {
                System.out.print("Nh???p t??n d???ch v??? c???a House - T??n d???ch v??? ph???i vi???t hoa k?? t??? ?????u, c??c k?? t??? sau l?? k?? t??? b??nh th?????ng: ");
                name = scanner.nextLine();
                if (FacilityExceptionUtils.nameServiceCheck(name)) {
                    return name;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Double addareaUse() {
        Double araeUse;
        while (true) {
            try {
                System.out.print("Nh???p di???n t??ch s??? d???ng c???a House - Di???n t??ch s??? d???ng ph???i l?? s??? th???c l???n h??n 30m2: ");
                araeUse = Double.parseDouble(scanner.nextLine());
                if (FacilityExceptionUtils.areaUseCheck(araeUse)) {
                    return araeUse;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Double addRentalCost() {
        Double rentalCost;
        while (true) {
            try {
                System.out.print("Nh???p chi ph?? thu?? c???a House - Chi ph?? thu?? ph???i l?? s??? d????ng: ");
                rentalCost = Double.parseDouble(scanner.nextLine());
                if (FacilityExceptionUtils.rentalCoatCheck(rentalCost)) {
                    return rentalCost;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int addMaxPerson() {
        int maxPerson;
        while (true) {
            try {
                System.out.print("S??? l?????ng ng?????i t???i ??a c???a House - S??? l?????ng ng?????i t???i ??a ph???i ph???i l???n h??n 0 v?? nh??? h??n 20: ");
                maxPerson = Integer.parseInt(scanner.nextLine());
                if (FacilityExceptionUtils.maxPersonCheck(maxPerson)) {
                    return maxPerson;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String addtypeOfRental() {
        String typeOfRental;
        while (true) {
            try {
                System.out.print("Nh???p ki???u thu?? c???a House: ");
                typeOfRental = scanner.nextLine();
                if (FacilityExceptionUtils.typeOfRentalCheck(typeOfRental)) {
                    return typeOfRental;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String addRoomStandard() {
        String roomStandard;
        while (true) {
            try {
                System.out.println("Nh???p ti??u chu???n ph??ng c???a House: ");
                roomStandard = scanner.nextLine();
                if (FacilityExceptionUtils.roomStandardCheck(roomStandard)) {
                    return roomStandard;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int addNumberOfFloors() {
        int numberOfFloors;
        while (true) {
            try {
                System.out.println("Nh???p s??? t???ng c???a House: ");
                numberOfFloors = Integer.parseInt(scanner.nextLine());
                if (FacilityExceptionUtils.numberOfFloorsCheck(numberOfFloors)) {
                    return numberOfFloors;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

//------------------------------------------

    private Room getInfoRoom() {
        String codeService = addRoomCode();
        String nameService = addRoomName();
        Double areaUse = addareaUseRoom();
        Double rentalCost = addRentalCostRoom();
        int maxPerson = addMaxPersonRoom();
        String typeOfRental = addTypeOfRentalRoom();
        String freeService = addFreeService();

        Room room = new Room(codeService, nameService, areaUse, rentalCost, maxPerson, typeOfRental, freeService);
        return room;

    }

    private String addRoomCode() {
        String code;
        while (true) {
            try {
                System.out.print("Nh???p m?? d???ch v??? c???a Room: ");
                code = scanner.nextLine();
                if (FacilityExceptionUtils.codeRoomCheck(code)) {
                    return code;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String addRoomName() {
        String name;
        while (true) {
            try {
                System.out.print("Nh???p t??n d???ch v??? c???a Room - T??n d???ch v??? ph???i vi???t hoa k?? t??? ?????u, c??c k?? t??? sau l?? k?? t??? b??nh th?????ng: ");
                name = scanner.nextLine();
                if (FacilityExceptionUtils.nameServiceCheck(name)) {
                    return name;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Double addareaUseRoom() {
        Double araeUse;
        while (true) {
            try {
                System.out.print("Nh???p di???n t??ch s??? d???ng c???a Room - Di???n t??ch s??? d???ng ph???i l?? s??? th???c l???n h??n 30m2: ");
                araeUse = Double.parseDouble(scanner.nextLine());
                if (FacilityExceptionUtils.areaUseCheck(araeUse)) {
                    return araeUse;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Double addRentalCostRoom() {
        Double rentalCost;
        while (true) {
            try {
                System.out.print("Nh???p chi ph?? thu?? c???a Room - Chi ph?? thu?? ph???i l?? s??? d????ng: ");
                rentalCost = Double.parseDouble(scanner.nextLine());
                if (FacilityExceptionUtils.rentalCoatCheck(rentalCost)) {
                    return rentalCost;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int addMaxPersonRoom() {
        int maxPerson;
        while (true) {
            try {
                System.out.print("S??? l?????ng ng?????i t???i ??a c???a Room - S??? l?????ng ng?????i t???i ??a ph???i ph???i l???n h??n 0 v?? nh??? h??n 20: ");
                maxPerson = Integer.parseInt(scanner.nextLine());
                if (FacilityExceptionUtils.maxPersonCheck(maxPerson)) {
                    return maxPerson;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String addTypeOfRentalRoom() {
        String typeOfRental;
        while (true) {
            try {
                System.out.print("Nh???p ki???u thu?? c???a Room: ");
                typeOfRental = scanner.nextLine();
                if (FacilityExceptionUtils.typeOfRentalCheck(typeOfRental)) {
                    return typeOfRental;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String addFreeService() {
        String freeService;
        while (true) {
            try {
                System.out.print("Nh???p d???ch v??? mi???n ph?? ??i k??m c???a Room: ");
                freeService = scanner.nextLine();
                if (FacilityExceptionUtils.freeServiceCheck(freeService)) {
                    return freeService;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //    ------------------------------------------
    public static LinkedHashMap<Villa, Integer> readVilla() throws IOException {
        villaList = new LinkedHashMap<>();
        BufferedReader bufferedReader = null;
        try {
            File file = new File(VILLA_FILE);
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = "";
            String[] arr;
            Villa villa;
            while ((line = bufferedReader.readLine()) != null) {
                arr = line.split(",");
                villa = new Villa(arr[0], arr[1], Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), Integer.parseInt(arr[4]), arr[5], arr[6], Double.parseDouble(arr[7]), Integer.parseInt(arr[8]));
                villaList.put(villa, 0);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Kh??ng t??m th???y file");
        } catch (IOException e) {
            System.out.println("Kh??ng ?????c ???????c file");
        }

        return villaList;
    }

    public static LinkedHashMap<House, Integer> readHouse() throws IOException {
        houseList = new LinkedHashMap<>();
        BufferedReader bufferedReader = null;
        try {
            File file = new File(HOUSE_FILE);
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = "";
            String[] arr;
            House house;
            while ((line = bufferedReader.readLine()) != null) {
                arr = line.split(",");
                house = new House(arr[0], arr[1], Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), Integer.parseInt(arr[4]), arr[5], arr[6], Integer.parseInt(arr[7]));

                houseList.put(house, 0);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Kh??ng t??m th???y file");
        } catch (IOException e) {
            System.out.println("Kh??ng ?????c ???????c file");
        }

        return houseList;
    }

    public static LinkedHashMap<Room, Integer> readRoom() throws IOException {
        roomList = new LinkedHashMap<>();
        BufferedReader bufferedReader = null;
        try {
            File file = new File(ROOM_FILE);
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = "";
            String[] arr;
            Room room;
            while ((line = bufferedReader.readLine()) != null) {
                arr = line.split(",");
                room = new Room(arr[0], arr[1], Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), Integer.parseInt(arr[4]), arr[5], arr[6]);

                roomList.put(room, 0);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Kh??ng t??m th???y file");
        } catch (IOException e) {
            System.out.println("Kh??ng ?????c ???????c file");
        }

        return roomList;
    }


    public void writeVillaFile(LinkedHashMap<Villa, Integer> villaList) {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(VILLA_FILE);
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Villa villa : villaList.keySet()) {
                bufferedWriter.write(villa.getInfo());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Kh??ng m??? ???????c file");
        }
    }

    public void writeHouseFile(LinkedHashMap<House, Integer> housrList) {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(HOUSE_FILE);
            bufferedWriter = new BufferedWriter(new FileWriter(file));

            for (House house : housrList.keySet()) {
                bufferedWriter.write(house.getInfo());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Kh??ng m??? ???????c file");
        }
    }

    public void writeRoomFile(LinkedHashMap<Room, Integer> roomList) {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(ROOM_FILE);
            bufferedWriter = new BufferedWriter(new FileWriter(file));

            for (Room room : roomList.keySet()) {
                bufferedWriter.write(room.getInfo());
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            System.out.println("Kh??ng m??? ???????c file");
        }
    }

    public static List<Facility> readMaintenanceList() throws IOException {
        maintenanceList = new ArrayList<>();
        File file = new File("src\\furama_resort\\data\\maintenanceFacility.csv");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] arr;
        while ((line = bufferedReader.readLine()) != null) {
            arr = line.split(",");
            if (line.contains("SVVL-")) {
                maintenanceList.add(new Villa(arr[0], arr[1], Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), Integer.parseInt(arr[4]), arr[5], arr[6], Double.parseDouble(arr[7]), Integer.parseInt(arr[8])));
            } else if (line.contains("VLHO-")) {
                maintenanceList.add(new House(arr[0], arr[1], Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), Integer.parseInt(arr[4]), arr[5], arr[6], Integer.parseInt(arr[7])));
            } else {
                maintenanceList.add(new Room(arr[0], arr[1], Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), Integer.parseInt(arr[4]), arr[5], arr[6]));

            }
        }
        bufferedReader.close();
        return maintenanceList;
    }

    public static void writeMaintenace(List<Facility> maintenanceList) throws IOException {
        File file = new File("src\\furama_resort\\data\\maintenanceFacility.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (Facility facility : maintenanceList) {
            bufferedWriter.write(facility.getInfo());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}
