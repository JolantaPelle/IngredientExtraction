import java.io._
import scala.io.Source

object IngredientExtraction extends App{
  // https://www.gutenberg.org/files/13177/13177-8.txt
  // cookbook file path:
  val cookbook = "src/resources/cookbook.txt"
  // result (recipe names with ingredients) file path:
  val result = "src/resources/result.txt"

  // reading the cookbook file:
  val source = Source.fromFile(cookbook)
  val text = source.mkString.split("\r\n\r\n\r\n")
  source.close

  // this is where each recipes name with its ingredients will be filtered out:
  def getRecipeIngredients(text:String) = {
    val lines = text.split("\r\n")
    val ingredients = lines.head +: lines.filter(line => line.length > 5 && line.substring(0,4) == "    " && line.substring(4,5) != " " && line.substring(4,5) != "\"")
    if (ingredients.length >= 2 && lines.head != "") ingredients.mkString("\n") // returns recipes name and ingredients listed
    else "" // returns empty
  }

  // doing the filtering:
  val ingredients = for (section <- text if getRecipeIngredients(section).nonEmpty) yield getRecipeIngredients(section)

  // writing the result file:
  val fw = new FileWriter(result)
  fw.write(ingredients.mkString("\r\n\r\n"))
  fw.close()
}
