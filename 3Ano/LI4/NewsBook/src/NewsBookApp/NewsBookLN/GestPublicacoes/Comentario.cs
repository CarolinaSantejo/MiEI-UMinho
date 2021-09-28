using System;

namespace NewsBookLN.GestPublicacoes
{

	public class Comentario
	{
		private int idPublicacao;
		private string userName;
		private string texto;
		private DateTime data;

		public Comentario(string userName, string texto, int idPub)
		{
			this.idPublicacao = idPub;
			this.userName = userName;
			this.texto = texto;
			this.data = DateTime.Now;
		}

		public Comentario(string userName,int idPublicacao, string texto, DateTime data)
		{
			this.idPublicacao = idPublicacao;
			this.userName = userName;
			this.texto = texto;
			this.data = data;
		}

		public virtual string UserName
		{
			get
			{
				return this.userName;
			}
			set
			{
				this.userName = value;
			}
		}
		public virtual string Texto
		{
			get
			{
				return this.texto;
			}
			set
			{
				this.texto = value;
			}
		}
		public virtual DateTime Data
		{
			get
			{
				return this.data;
			}
			set
			{
				this.data = value;
			}
		}

		public virtual int IdPublicacao
		{
			get
			{
				return this.idPublicacao;
			}
			set
			{
				this.idPublicacao = value;
			}
		}

		/*public virtual string DataString
		{
			get
			{
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				return this.data.format(format);
			}
		}*/
	}


}