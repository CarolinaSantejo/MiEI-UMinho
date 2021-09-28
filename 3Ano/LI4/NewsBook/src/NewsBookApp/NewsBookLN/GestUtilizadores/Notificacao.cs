using System;

namespace NewsBookLN.GestUtilizadores
{

	public class Notificacao
	{
		private string userName;
		private int pubID;
		private DateTime dataHora;
		public Notificacao() { }

		public Notificacao(int pubID,string username)
		{
			this.userName = username;
			this.pubID = pubID;
			this.dataHora = DateTime.Now;
		}

		public Notificacao(DateTime data,int idPublicacao, string userName)
		{
			this.userName = userName;
			this.pubID = idPublicacao;
			this.dataHora = data;
		}

		public virtual int IdPublicacao
		{
			get
			{
				return this.pubID;
			}
			set
			{
				this.pubID = value;
			}
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
		public virtual DateTime Data
		{
			get
			{
				return this.dataHora;
			}
			set
			{
				this.dataHora = value;
			}
		}

		/*public virtual string DataString
		{
			get
			{
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				return this.dataHora.format(format);
			}
		}*/

	}
}