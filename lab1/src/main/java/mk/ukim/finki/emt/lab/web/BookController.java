package mk.ukim.finki.emt.lab.web;

//
//import mk.ukim.finki.emt.lab.model.Book;
//import mk.ukim.finki.emt.lab.model.dto.BookDto;
//import mk.ukim.finki.emt.lab.service.AuthorService;
//import mk.ukim.finki.emt.lab.service.BookService;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/books")
//public class BookController {
//
//    private final BookService bookService;
//    private final AuthorService authorService;
//
//    public BookController(BookService bookService, AuthorService authorService) {
//        this.bookService = bookService;
//        this.authorService = authorService;
//    }
//
//    @GetMapping()
//    public List<Book> findAll() {
////            @RequestParam(required = false)String guestName,
////                          @RequestParam(required = false)RoomType roomType,
////                          @RequestParam(required = false)Long hotel,
////                          @RequestParam(defaultValue = "1")Integer pageNum,
////                          @RequestParam(defaultValue = "20")Integer pageSize,
////                          Model model) {
////
////        Page<Reservation> page = this.bookService.findPage(guestName, roomType, hotel, pageNum-1, pageSize);
////        model.addAttribute("page", page);
////        model.addAttribute("reservations", this.bookService.listAll());
////        model.addAttribute("roomTypes", Arrays.stream(RoomType.values()).toList());
////        model.addAttribute("guestName", guestName);
////        model.addAttribute("roomType", roomType);
////        model.addAttribute("hotel", hotel);
////        model.addAttribute("hotels", this.authorService.listAll());
//
//
//        return "";
//    }
//
//
//    @GetMapping()
//    public String showAdd(Model model) {
////        List<Hotel> hotels = this.authorService.listAll();
////        model.addAttribute("hotels", hotels);
////        model.addAttribute("roomTypes", Arrays.stream(RoomType.values()).toList());
//////        model.addAttribute("reservations", this.hotelService.listAll());
//
//        return "";
//    }
//
//
//    @GetMapping()
//    public String showEdit(@PathVariable Long id, Model model) {
////        model.addAttribute("hotels", this.authorService.listAll());
////        model.addAttribute("roomTypes", Arrays.stream(RoomType.values()).toList());
////        model.addAttribute("reservation", this.bookService.findById(id));
//        return "";
//    }
//
//
//    @PostMapping("/add")
//    public ResponseEntity<Book> create(@RequestBody BookDto book) {
//    }
//
//
//    @PutMapping("/edit/{id}")
//    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody BookDto book ){
//    }
//
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id){
//    }
//
//
//    @PostMapping()
//    public String extend(@PathVariable Long id) {
////        this.bookService.extendStay(id);
//        return "";
//    }
//}



import mk.ukim.finki.emt.lab.model.Book;
import mk.ukim.finki.emt.lab.model.dto.BookDto;
import mk.ukim.finki.emt.lab.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public List<Book> findAll() {
        return bookService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Book> create(@RequestBody BookDto bookDto) {
        return bookService.create(bookDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return bookService.edit(id, bookDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (bookService.findById(id).isPresent()) {
            bookService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/rentOut/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Book> rentBook(@PathVariable Long id) {
        return bookService.isRented(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
