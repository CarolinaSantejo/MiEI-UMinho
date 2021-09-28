using System.Collections.Generic;

namespace NewsBookLN.GestUtilizadores
{

	public class Jornalista : Utilizador
	{

		public Jornalista() : base()
        {	
        }
        public Jornalista(string username)
		{
			base.UserName = username;
			Biografia = null;
			LinkedIn = null;
			Seguidores = null;
        }

		public Jornalista(string idPais, string username, string email, string password, string nome,string cidade, string bio, string linkedin) : base(idPais, username, email, password, nome, cidade)
		{
			Biografia = bio;
			LinkedIn = linkedin;
			Seguidores = new List<Utilizador>();
		}
		public string Biografia { get; set; }
        public string LinkedIn { get; set; }

		public ICollection<Utilizador> Seguidores { get; set; }

	}
}