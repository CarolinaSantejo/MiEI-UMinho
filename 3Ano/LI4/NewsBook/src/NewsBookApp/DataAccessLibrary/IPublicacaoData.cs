using NewsBookLN.GestPublicacoes;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public interface IPublicacaoData
    {
        Task<List<Publicacao>> GetPublicacao(int id);
        Task<List<Publicacao>> GetPublicacoes();
        Task<List<Noticia>> GetNoticia(int id);
        Task<List<int>> GetNoticiasGuardadas(string userName);
        public Task InsertNoticia(Noticia n);
        public Task InsertNoticiaInPub(Noticia n);
        public Task InsertNoticiaGuardada(Noticia n, string username);
        Task deleteNoticiaGuardada(int idPub, string username);
        Task deleteNoticiaInPub(int idPub);
        Task deleteNoticia(int idPub);
    }
}