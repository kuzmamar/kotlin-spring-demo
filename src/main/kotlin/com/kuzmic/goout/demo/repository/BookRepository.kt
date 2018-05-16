package com.kuzmic.goout.demo.repository

import com.kuzmic.goout.demo.model.Book
import com.sun.javaws.exceptions.InvalidArgumentException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *  Simple DTO for books. H2 relational database id used for simplicity and demonstration.
 */
interface BookRepository {
    fun getAll(): Collection<Book>
    fun findById(id: Long): Book
    fun insert(b: Book): Boolean
}

@Service
@Transactional
class JdbcBookRepository (private val operations: JdbcOperations): BookRepository {
    override fun getAll(): Collection<Book> =
            operations.query("select * from books") { result, _ ->
                Book(
                        result.getString("isbn"),
                        result.getString("title"),
                        result.getLong("id")
                )
            }

    override fun findById(id: Long): Book =
            operations.queryForObject("select * from books where id = ?", id) { result, _ ->
                if (result.wasNull()) {
                    throw InvalidArgumentException(arrayOf("Book with id $id not found."))
                }
                Book(
                        result.getString("isbn"),
                        result.getString("title"),
                        result.getLong("id"))
            }!!


    override fun insert(b: Book): Boolean =
            operations.execute("insert into books(isbn, title) values (?, ?)") { it ->
                it.setString(1, b.isbn)
                it.setString(2, b.title)
                it.execute()
            }!!

}

