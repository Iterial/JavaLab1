package io.github.mykytko.lab1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.YearMonth;

public class Main {
    private static ArrayList<Car> cars = new ArrayList<Car>();

    public static void main(String[] args) {

        var url = "https://raw.githubusercontent.com/rashida048/Datasets/master/cars.csv";
        var fileName = "cars.csv";

        List<String> lines;
        try {
            downloadData(url, fileName);
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        var rand = new Random();

        for (int i = 1; i < lines.size(); i++) {
            var splits = lines.get(i).split(",");
            var brand = splits[1];
            var model = splits[2];
            var year = Integer.parseInt(splits[0]);
            var color = rand.nextInt(0xffffff + 1);
            var price = 10000 + rand.nextInt(400) * 100 - rand.nextInt(1);
            var registrationNumber = generateRegistrationNumber();

            var car = new Car(brand, model, year, color, price, registrationNumber);
            cars.add(car);
        }

        for (var car : cars) {
            System.out.println(car);
            System.out.println();
        }

        ArrayList<Car> carsWithBrand = taskA("tesla");
        ArrayList<Car> carsWithModelYear = taskB("MODEL S (60 kWh battery)", 8);

        System.out.println("Cars with a specific brand:\n");

        for (var car : carsWithBrand) {
          System.out.println(car);
          System.out.println();
        }

        System.out.println("\nCars with a specific model that were used for n or more years:\n");

        for (var car : carsWithModelYear) {
          System.out.println(car);
          System.out.println();
        }
    }

    private static String generateRegistrationNumber() {
        return "";
    }

    private static ArrayList<Car> taskA(String brand) {
        ArrayList<Car> carsList = new ArrayList<Car>();

        for (Car car : cars) {
          if (car.getBrand().equalsIgnoreCase(brand)) {
            carsList.add(car);
          }
        }

        return carsList;
    }

    private static ArrayList<Car> taskB(String model, int usedFor) {
      ArrayList<Car> carsList = new ArrayList<Car>();
      int currentYear = YearMonth.now().getYear();

      for (Car car : cars) {
        if (
          car.getModel().equalsIgnoreCase(model)
          && (currentYear - car.getYear()) > usedFor
        ) {
          carsList.add(car);
        }
      }

      return carsList;
    }

    private static ArrayList<Car> taskC(ArrayList<Car> cars) {
        return null;
    }

    private static void downloadData(String url, String fileName) throws IOException {
        var readableByteChannel = Channels.newChannel(new URL(url).openStream());
        var fileOutputStream = new FileOutputStream(fileName);
        var fileChannel = fileOutputStream.getChannel();

        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileChannel.close();
        fileOutputStream.close();
        readableByteChannel.close();
    }
}