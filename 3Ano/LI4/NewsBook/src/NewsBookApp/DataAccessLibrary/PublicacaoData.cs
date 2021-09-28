using DataLibrary;
using NewsBookLN.GestPublicacoes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public class PublicacaoData : IPublicacaoData
    {
        private readonly ISQLDataAccess _dbPub;

        public PublicacaoData(ISQLDataAccess db)
        {
            _dbPub = db;
        }

        public Task<List<Publicacao>> GetPublicacoes()
        {
            string sql = "select * from dbo.Publicacao";
            return _dbPub.LoadData<Publicacao, dynamic>(sql, new { });
        }

        public Task<List<Publicacao>> GetPublicacao(int id)
        {
            string sql = "select * from dbo.Publicacao where IdPublicacao = '" + id + "'";
            return _dbPub.LoadData<Publicacao, dynamic>(sql, new { });
        }

        public Task<List<Noticia>> GetNoticia(int id)
        {
            string sql = "select * from dbo.Noticia where IdPublicacao = '" + id + "'";
            return _dbPub.LoadData<Noticia, dynamic>(sql, new { });
        }
        public Task<List<int>> GetNoticiasGuardadas(string userName)
        {
            string sql = "select idPublicacao from dbo.Utilizador_Noticia where UserName = '" + userName + "'";
            return _dbPub.LoadData<int, dynamic>(sql, new { });
        }

        public Task InsertNoticia(Noticia n)
        {
            string sql = @"insert into dbo.Noticia (Url,Autor,IdPublicacao)
                          values (@Url, @Autor, @IdPublicacao);";

            return _dbPub.SaveData(sql, n);
        }

        public Task deleteNoticiaGuardada(int idPub, string username)
        {
            string sql = @"delete from dbo.Utilizador_Noticia where IdPublicacao='"+ idPub+"' and UserName ='"+username+"';";

            return _dbPub.SaveData(sql, new { });
        }

        public Task InsertNoticiaInPub(Noticia n)
        {
            string sql = @"insert into dbo.Publicacao (IdPublicacao,Titulo,Data,Descricao,Imagem)
                          values (@IdPublicacao, @Titulo,@Data, @Descricao, @Imagem);";
            return _dbPub.SaveData(sql, n);
        }


        public Task InsertNoticiaGuardada(Noticia n, string username)
        {
            string sql = @"insert into dbo.Utilizador_Noticia (Username,IdPublicacao,Data)
                          values ('" + username + "'," + "@IdPublicacao, @Data);";

            return _dbPub.SaveData(sql, n);
        }

        public Task deleteNoticiaInPub(int idPub)
        {
            string sql = @"delete from dbo.Publicacao where IdPublicacao='" + idPub + "';";

            return _dbPub.SaveData(sql, new { });
        }

        public Task deleteNoticia(int idPub)
        {
            string sql = @"delete from dbo.Noticia where IdPublicacao='" + idPub + "';";

            return _dbPub.SaveData(sql, new { });
        }
    }
}
