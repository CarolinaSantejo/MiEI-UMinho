using NewsBookLN.GestUtilizadores;
using NewsBookLN.GestPublicacoes;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DataAccessLibrary
{
    public interface IPeopleData
    {
        Task<List<Utilizador>> GetPeople();
        Task<List<Utilizador>> GetPerson(string userName);
        Task InsertPerson(Utilizador person);

        Task InsertComment(Comentario c);

        Task updateUserProfile(Utilizador person);
        Task<List<Comentario>> GetComentarios(int idPub);

        Task<List<int>> getFavCategouries(string userName);

        Task InsertCategoriaFav(string username, int idCat);
        Task UpdateComment(Comentario c);
        Task<List<Notificacao>> GetNotifications(string userName);
        Task InsertNotification(Notificacao n);
        Task deleteAllNotifications(string username);
        Task InsertFollow(string username, string jornalista);
        Task RemoveFollow(string username, string jornalista);
    }
}