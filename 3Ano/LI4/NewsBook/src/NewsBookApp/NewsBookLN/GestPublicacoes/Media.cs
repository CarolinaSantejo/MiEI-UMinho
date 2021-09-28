using System;

namespace NewsBookLN.GestPublicacoes
{

	public class Media : Publicacao
	{

		private string url;
		private string autor;

		public Media(int pubId, string titulo, DateTime data, string desc, string img, string autor, string url) : base(pubId, titulo, data, desc, img)
		{
			this.autor = autor;
			this.url = url;
		}

		public virtual string Url
		{
			get
			{
				return this.url;
			}
			set
			{
				this.url = value;
			}
		}
		public virtual string Autor
		{
			get
			{
				return this.autor;
			}
			set
			{
				this.autor = value;
			}
		}

	}
}