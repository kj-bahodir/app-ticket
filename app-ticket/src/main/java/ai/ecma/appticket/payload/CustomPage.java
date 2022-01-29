package ai.ecma.appticket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomPage<T> {
    private List<T> content; // Elementlar
    private int numberOfElements; // Current page dagi elementlar soni
    private int number; // Current page number
    private long totalElements; // Barcha elementlar soni
    private int totalPages; //Barcha page lar soni
    private int size; // Nechta so'ragani

//    public CustomPage(int numberOfElements, int number, long totalElements, int totalPages, int size) {
//        this.numberOfElements = numberOfElements;
//        this.number = number;
//        this.totalElements = totalElements;
//        this.totalPages = totalPages;
//        this.size = size;
//    }
//
//    public CustomPage(List<T> content, int number, int totalPages, int size) {
//        this.content = content;
//        this.number = number;
//        this.totalPages = totalPages;
//        this.size = size;
//    }
}
