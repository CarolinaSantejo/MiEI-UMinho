using NewsBookLN.GestUtilizadores;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public interface ICategoriaData
    {
        Task<List<Categoria>> GetCategorias();
        Task<List<Categoria>> GetCategoria(int cat);

        public Task InsertUserCategorias(Utilizador person);
        Task<List<Categoria>> GetCategoriaUser(string u);
        Task deleteCategoriasFav(string username, List<int> idCats);
    }
}