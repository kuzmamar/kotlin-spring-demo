package com.kuzmic.goout.demo.controller

import com.kuzmic.goout.demo.repository.BookRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * Simple rest routes for exposing books
 */
@RestController
class BookRestController(private val bookRepository: BookRepository) {

    @GetMapping("/books")
    fun list() = bookRepository.getAll()

    @GetMapping("/books/{id:\\d+}")
    fun show(@PathVariable(name = "id") id: Long) = bookRepository.findById(id)
}