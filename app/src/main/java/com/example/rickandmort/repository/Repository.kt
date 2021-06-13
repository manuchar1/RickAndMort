package com.example.rickandmort.repository




import com.example.rickandmort.data.models.Character
import com.example.rickandmorty.data.network.RetrofitInstance
import com.example.rickandmorty.db.CharactersDatabase

class Repository(val db: CharactersDatabase) {


    suspend fun getCharacters(pageNumber:Int) =
        RetrofitInstance.api.getCharacters(pageNumber)


    suspend fun searchCharacter(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForCharacters(searchQuery,pageNumber)

    suspend fun upsert(character:Character) = db.getCharacterDao().upsert(character)

    fun getSavedCharacter() = db.getCharacterDao().getAllCharacters()

    suspend fun deleteCharacter(character:Character) = db.getCharacterDao().deleteCharacter(character)




/*    private val charactersApi: CharactersAPI by lazy { RetrofitInstance.api }
    private val responseHandler: ResponseHandler by lazy { ResponseHandler() }

    suspend fun getCharacters(page: Int): Resource<CharactersResponse<List<Character>>> {

        val response: Response<CharactersResponse<List<Character>>>

        try {
            response = charactersApi.getCharacters(page)

            if (response.isSuccessful) {
                response.body()?.let {
                    return responseHandler.handleSuccess(it)
                }
                return responseHandler.handleDefaultException()
            } else {
                return responseHandler.handleDefaultException()
            }

        } catch (e: Exception) {
            return responseHandler.handleException(e)
        }
    }*/
}