using System;
using DataLibrary;
using NewsBookLN.GestPublicacoes;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public class ArtigoData : IArtigoData
    {
        private readonly ISQLDataAccess _dbArt;

        public ArtigoData(ISQLDataAccess db)
        {
            _dbArt = db;
        }

        public Task<List<Artigo>> GetArtigos()
        {
            string sql = "select * from dbo.ArtigoOpiniao";
            return _dbArt.LoadData<Artigo, dynamic>(sql, new { });
        }

        public Task<List<Artigo>> GetArtigosUser(string id)
        {
            string sql = "select * from dbo.ArtigoOpiniao where UserNameJornalista = '" + id + "'";
            return _dbArt.LoadData<Artigo, dynamic>(sql, new { });
        }

        public Task<List<Artigo>> GetArtigo(int id, string usern)
        {
            string sql = "select * from dbo.ArtigoOpiniao where IdPublicacao = '" + id + "' and " +
                "UserNameJornalista = '" + usern + "'";
            return _dbArt.LoadData<Artigo, dynamic>(sql, new { });
        }

        public Task<List<Artigo>> GetArtigoById(int id)
        {
            string sql = "select * from dbo.ArtigoOpiniao where IdPublicacao = '" + id + "'";
            return _dbArt.LoadData<Artigo, dynamic>(sql, new { });
        }

        public Task InsertArtigo(Artigo n)
        {
            string sql = @"insert into dbo.ArtigoOpiniao (UserNameJornalista,Conteudo,IdPublicacao)
                          values (@UserNameJornalista, @Conteudo, @IdPublicacao);";

            return _dbArt.SaveData(sql, n);
        }

        public Task InsertArtigoInPub(Artigo n)
        {
            string sql = @"insert into dbo.Publicacao (IdPublicacao,Titulo,Data,Descricao,Imagem)
                          values (@IdPublicacao, @Titulo,@Data, @Descricao, @Imagem);";
            return _dbArt.SaveData(sql, n);
        }

    }
}
