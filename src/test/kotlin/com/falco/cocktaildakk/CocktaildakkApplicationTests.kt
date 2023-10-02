package com.falco.cocktaildakk

import com.falco.cocktaildakk.domain.dto.Cocktail
import com.falco.cocktaildakk.repository.CocktailRepository
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.io.File

@SpringBootTest
class CocktaildakkApplicationTests {

    @Autowired
    private lateinit var cocktailRepository: CocktailRepository

    @Test
    fun cocktailGetTest() {
        println(cocktailRepository.findByIdOrNull(87))
        println(cocktailRepository.findByIdOrNull(88))
    }


    @Test
    fun saveDumpData() {
        val file = File("src/main/resources/dump/cocktail_recipes_dump.xlsx")
        val workbook = WorkbookFactory.create(file)
        cocktailRepository.deleteAll()

        val sheet = workbook.getSheetAt(0)
        for (rowIndex in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(rowIndex)
            val cocktail = Cocktail(
                id = rowIndex.toLong(),
                englishName = row.getCell(1).stringCellValue,
                koreanName = row.getCell(2).stringCellValue,
                alcoholLevel = row.getCell(3).numericCellValue.toInt(),
                baseLiquor = row.getCell(4).stringCellValue,
                mixingMethod = row.getCell(5).stringCellValue,
                ingredients = row.getCell(6).stringCellValue,
                keywords = row.getCell(7).stringCellValue,
                description = row.getCell(8).stringCellValue
            )
            cocktailRepository.save(cocktail)
        }

        workbook.close()
    }
}
