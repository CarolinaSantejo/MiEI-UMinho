using NewsBookLN.GestPublicacoes;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public interface IArtigoData
    {
        Task<List<Artigo>> GetArtigo(int id, string usern);
        Task<List<Artigo>> GetArtigos();

        Task<List<Artigo>> GetArtigosUser(string id);
        Task<List<Artigo>> GetArtigoById(int id);
        Task InsertArtigo(Artigo n);
        Task InsertArtigoInPub(Artigo n);

    }
}