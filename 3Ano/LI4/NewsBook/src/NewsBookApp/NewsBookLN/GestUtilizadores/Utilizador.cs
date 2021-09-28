using System.Collections.Generic;

namespace NewsBookLN.GestUtilizadores
{
	using Noticia = NewsBookLN.GestPublicacoes.Noticia;

	public class Utilizador
	{
		private List<int> categoriasFav;
		private IDictionary<int, Noticia> savedNews;
		private ICollection<Notificacao> notifs;
		private ICollection<int> idJornalistasSegue;

		public Utilizador()
		{
			Country_IdCountry = "";
			UserName = "";
			Email = "";
			Password = "";
			Cidade = null;
			UrlFoto = null; //=default image
			Nome = "";
			this.categoriasFav = new List<int>();
			this.savedNews = new Dictionary<int, Noticia>();
			this.notifs = new List<Notificacao>();
			this.idJornalistasSegue = new List<int>();
		}

		public Utilizador(string idPais, string username, string email, string password, string nome)
		{
			Country_IdCountry = idPais;
			UserName = username;
			Email = email;
			Password = password;
			Cidade = null;
			UrlFoto = null; //=default image
			Nome = nome;
			this.categoriasFav = new List<int>();
			this.savedNews = new Dictionary<int, Noticia>();
			this.notifs = new List<Notificacao>();
			this.idJornalistasSegue = new List<int>();
		}

		public Utilizador(string idPais, string username, string email, string password, string nome, string cidade)
		{
			Country_IdCountry = idPais;
			UserName = username;
			Email = email;
			Password = password;
			Cidade = cidade;
			UrlFoto = null; //=default image
			Nome = nome;
			this.categoriasFav = new List<int>();
			this.savedNews = new Dictionary<int, Noticia>();
			this.notifs = new List<Notificacao>();
			this.idJornalistasSegue = new List<int>();
		}

		public string Nome { get; set; }
		public string UserName { get; set; }
		public string Password { get; set; }
		public string Country_IdCountry { get; set; }
		public string Email { get; set; }
		public string Cidade { get; set; }
		public string Fotografia { get; set; }

		public string UrlFoto { get; set; }


		public virtual List<int> CategoriasFav
		{
			get
			{
				return this.categoriasFav;
			}
			set
			{
				this.categoriasFav = value;
			}
		}
		public virtual IDictionary<int, Noticia> SavedNews
		{
			get
			{
				return this.savedNews;
			}
			set
			{
				this.savedNews = value;
			}
		}
		public virtual ICollection<Notificacao> Notifs
		{
			get
			{
				return this.notifs;
			}
			set
			{
				this.notifs = value;
			}
		}
		public virtual ICollection<int> IdJornalistasSegue
		{
			get
			{
				return this.idJornalistasSegue;
			}
			set
			{
				this.idJornalistasSegue = value;
			}
		}

		public virtual void addCategoria(int idCat)
		{
			this.categoriasFav.Add(idCat);
		}

		public virtual void removeCategoria(int idCat)
		{
			this.categoriasFav.Remove(idCat);
		}

		public virtual void addJornalista(int idJornalista)
		{
			this.idJornalistasSegue.Add(idJornalista);
		}

		public virtual void removeJornalista(int idJornalista)
		{
			this.idJornalistasSegue.Remove(idJornalista);
		}
	}
}