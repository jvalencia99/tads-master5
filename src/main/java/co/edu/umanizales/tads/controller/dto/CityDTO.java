package co.edu.umanizales.tads.controller.dto;

 public class CityDTO {
     public class CiudadDTO {
         private String cityName;
         private int maleCount;
         private int femaleCount;
         private int totalCount;

         public CiudadDTO(String cityName, int maleCount, int femaleCount, int totalCount) {
             this.cityName = cityName;
             this.maleCount = maleCount;
             this.femaleCount = femaleCount;
             this.totalCount = totalCount;
         }

         // getters and setters
     }
    // getters and setters
}


