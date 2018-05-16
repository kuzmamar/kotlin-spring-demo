package com.kuzmic.goout.demo

import com.kuzmic.goout.demo.model.Book
import com.kuzmic.goout.demo.repository.BookRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    /**
     * There are many options how to configure beans in Spring. (xml, java config, component scan).
     * Kotlin is nice, because we can write our own DSL (domain specific language). Spring integration with kotlin
     * allows you to register beans with its DSL as can be seen below.
     */
    SpringApplicationBuilder()
            .sources(DemoApplication::class.java)
            .initializers(beans {

                bean {
                    ApplicationRunner {
                        val bookRepository = ref<BookRepository>()

                        val books = arrayOf(
                            arrayOf("978-1-4028-9462-6", "Hello Spring!"),
                            arrayOf("978-1-4028-9462-4", "Hello Kotlin!"),
                            arrayOf("978-1-4028-9462-3", "Hello Java!")
                        ).map { Book(isbn = it[0], title = it[1]) }

                        for (book in books) {
                            bookRepository.insert(book)
                        }

                        bookRepository.getAll().forEach { println(it) }
                    }
                }

            }).run(* args)
}
