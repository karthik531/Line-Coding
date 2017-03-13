//initial state is low
//for nrzi--->1=transition,0=no transition
//for differential manchester--->1=no transition,0=transition
//for manchester--->logic 0=low to high , logic 1=high to low
import java.awt.*;
import java.awt.event.*;

class LineEncoding extends Frame implements ActionListener
{
	TextField tin;
	Button bnrz,bnrzl,bnrzi,brz,bman,bdman;
	Label lin;
	boolean nrz,nrzl,nrzi,rz,man,dman;
	int state;
	String str;

	LineEncoding( )
	{
		//setExtendedState(MAXIMIZED_BOTH); //used to make the frame fullscreen 
		setVisible(true);												//dynamically based on user resolution
		setSize(1366,768);
		setLayout(new FlowLayout( ));
		setBackground(Color.YELLOW);
		lin=new Label("Input");
		tin=new TextField(20);
		add(lin);
		add(tin);

		bnrz=new Button("NRZ");
		bnrzl=new Button("NRZ-L");
		bnrzi=new Button("NRZ-I");
		brz=new Button("RZ");
		bman=new Button("Manchester");
		bdman=new Button("Differential Manchester");
		add(bnrz) ; add(bnrzl) ; add(bnrzi) ; add(brz) ; add(bman) ; add(bdman) ;

		bnrz.addActionListener(this);
		bnrzl.addActionListener(this);
		bnrzi.addActionListener(this);
		brz.addActionListener(this);
		bman.addActionListener(this);
		bdman.addActionListener(this);

		addWindowListener(new WindowAdapter( )
		{
			public void windowClosing(WindowEvent a)
			{System.exit(1);}
		});
	}

	public static void main(String args[ ])
	{
		LineEncoding le=new LineEncoding( );
	}

	public void paint(Graphics g)
	{
		//initialize
		int x1=50,y1=380,x2=0,y2=0;
		//draw 5V,0V and -5V 
		g.setFont(new Font("Calibri",Font.BOLD,15)); //set font of string
		g.setColor(Color.BLUE);
		g.drawString("5V",x1-21,y1-50);		//adjusting string just before reference line
		g.drawString("0V",x1-21,y1);
		g.drawString("-5V",x1-25,y1+50);
		//reference line
		Graphics2D g2=(Graphics2D)g;		//for drawing thick lines should use Graphics2D
		g2.setStroke(new BasicStroke(3));	//thick line with width=3
		g2.setColor(Color.RED);
		g2.drawLine(50,380,2000,380);
		g2.drawLine(50,560,50,220);
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(2));	//thick line with width=2
		
		if(nrz==true)
		{
			x2=x1 ; y2=y1 ;
			int i=0;
			while(i<str.length( ))
			{	
				if(i!=0)
				{
					x2=x1 ; y2=y1+50;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
				}//if
				while(i<str.length( ) && str.charAt(i)=='0')
				{
					x2=x1+50 ; 
					g.drawLine(x1,y1,x2,y2);
					x1=x2 ; 
					i++;
				}//while state 0
				if(i<str.length( ))		//to avoid redundant transition lines from 0 to 1
				{
					x2=x1 ; y2=y1-50;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
				}
				while(i<str.length( ) && str.charAt(i)=='1')
				{
					x2=x1+50 ; 
					g.drawLine(x1,y1,x2,y2);
					x1=x2 ;
					i++;
				}//while state 1
			}//while i<length
		}//if nrz

		else if(nrzl==true)
		{
			y1=y1+50 ; x2=x1 ; y2=y1 ;
			int i=0;
			while(i<str.length( ))
			{	
				if(i!=0)
				{
					x2=x1 ; y2=y1+100; 
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
				}//if
				while(i<str.length( ) && str.charAt(i)=='0')
				{
					x2=x1+50 ; 
					g.drawLine(x1,y1,x2,y2);
					x1=x2 ; 
					i++;
				}//while state 0
				if(i<str.length( ))		//to avoid redundant transition lines from 0 to 1
				{
					x2=x1 ; y2=y1-100;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
				}
				while(i<str.length( ) && str.charAt(i)=='1')
				{
					x2=x1+50 ; 
					g.drawLine(x1,y1,x2,y2);
					x1=x2 ; 
					i++;
				}//while state 1
			}//while i<length
		}//if nrzl
		
		else if(nrzi==true)		//assume initial state is low
		{
			y1=y1+50 ; x2=x1 ; y2=y1 ;
			int i=0;
			while(i<str.length( ))
			{	
				while(i<str.length( ) && str.charAt(i)=='0')
				{
					x2=x1+50 ; 
					g.drawLine(x1,y1,x2,y2);
					x1=x2 ; 
					i++;
				}//while state 0
				/*x2=x1 ; y2=y1-100;
				g.drawLine(x1,y1,x2,y2);
				y1=y2;*/
				while(i<str.length( ) && str.charAt(i)=='1')
				{
					if(y1==430)
					{
						x2=x1 ; y2=y1-100;
						g.drawLine(x1,y1,x2,y2);
						y1=y2;
					}
					else if(y1==330)
					{
						x2=x1 ; y2=y1+100; 
						g.drawLine(x1,y1,x2,y2);
						y1=y2;
					}
					x2=x1+50 ; 
					g.drawLine(x1,y1,x2,y2);
					x1=x2 ; 
					i++;
				}//while state 1
			}//while i<length
		}//if nrzi

		else if(rz==true)
		{
			x2=x1 ; y2=y1 ;
			int i=0;
			while(i<str.length( ))
			{
				if(i<str.length( ) && str.charAt(i)=='0')
				{
					y2=y1+50;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
					y2=y1-50;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
				}//if state 0
				if(i<str.length( ) && str.charAt(i)=='1')
				{
					y2=y1-50;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
					y2=y1+50;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
				}//if state 1
				i++;
			}//while i<length
		}//if rz

	else if(man==true)
	{
		y1=y1+50 ; x2=x1 ; y2=y1 ;
		int i=0;
		while(i<str.length( ))
			{
				if(i<str.length( ) && str.charAt(i)=='0')
				{
					if(y1==330)
					{
						y2=y1+100;
						g.drawLine(x1,y1,x2,y2);
						y1=y2;
					}
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
					y2=y1-100;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
				}//if state 0
				if(i<str.length( ) && str.charAt(i)=='1')
				{
					if(y1==430)
					{
						y2=y1-100;
						g.drawLine(x1,y1,x2,y2);
						y1=y2;
					}
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
					y2=y1+100;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
				}//if state 1
				i++;
			}//while i<length
		}//if manchester

		else if(dman==true)		//0=transition , 1=no transition
		{
			y1=y1+50 ; x2=x1 ; y2=y1 ;
			int i=0;
			while(i<str.length( ))
			{
				if(i<str.length( ) && str.charAt(i)=='1')
				{
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
					if(y1==430)
						y2=y1-100;
					else if(y1==330)
						y2=y1+100;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
				}//if state 1
				if(i<str.length( ) && str.charAt(i)=='0')
				{
					if(y1==330)
						y2=y1+100;
					else if (y1==430)
						y2=y1-100;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
					if(y1==330)
						y2=y1+100;
					else if (y1==430)
						y2=y1-100;
					g.drawLine(x1,y1,x2,y2);
					y1=y2;
					x2=x1+25;
					g.drawLine(x1,y1,x2,y2);
					x1=x2;
				}//if state 0
				i++;
			}//while i<length
		}//if differential manchester
	}//paint

	public void actionPerformed(ActionEvent a)
	{
		String action=a.getActionCommand( );
		if(action.equals("NRZ"))
		{
			nrz=true ; nrzl=false ; nrzi=false ; rz=false ; man=false ; dman=false ;
			str=tin.getText( );
			repaint( );
		}
		else if(action.equals("NRZ-L"))
		{
			nrzl=true ; nrz=false ; nrzi=false ; rz=false ; man=false ; dman=false ;
			str=tin.getText( );
			repaint( );
		}
		else if(action.equals("NRZ-I"))
		{
			nrzi=true ; nrz=false ; nrzl=false ; rz=false ; man=false ; dman=false ;
			str=tin.getText( );
			repaint( );
		}
		else if(action.equals("RZ"))
		{
			rz=true; ; nrzi=false ; nrz=false ; nrzl=false ; man=false ; dman=false ;
			str=tin.getText( );
			repaint( );
		}
		else if(action.equals("Manchester"))
		{
			rz=false; ; nrzi=false ; nrz=false ; nrzl=false ; man=true ; dman=false ;
			str=tin.getText( );
			repaint( );
		}
		else if(action.equals("Differential Manchester"))
		{
			rz=false; ; nrzi=false ; nrz=false ; nrzl=false ; man=false ; dman=true ;
			str=tin.getText( );
			repaint( );
		}
	}
}