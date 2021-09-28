using System;

namespace NewsBookLN.GestPublicacoes
{

	public class Artigo : Publicacao
	{

		private string idJornalista;
		private string conteudo;

		public Artigo() : base()
        {

        }
		public Artigo(string titulo, DateTime data, string desc, string img, string idJornalista, string conteudo) : base(titulo, data, desc, img)
		{
			this.idJornalista = idJornalista;
			this.conteudo = conteudo;
		}
		public Artigo(int pubId, string titulo, DateTime data, string desc, string img, string idJornalista, string conteudo) : base(pubId,titulo, data, desc, img)
		{
			this.idJornalista = idJornalista;
			this.conteudo = conteudo;
		}

		public virtual string UserNameJornalista
		{
			get
			{
				return idJornalista;
			}
			set
			{
				this.idJornalista = value;
			}
		}
		public virtual string Conteudo
		{
			get
			{
				return conteudo;
			}
			set
			{
				this.conteudo = value;
			}
		}
		

	}
}