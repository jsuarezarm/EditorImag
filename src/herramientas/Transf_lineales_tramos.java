/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package herramientas;

import static editorimag.EditorImag.gestor_img;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import imagen.Imagen;
import herramientas.VentanaTransfTramos;
import java.awt.GridLayout;

/**
 *
 * @author jonay
 */
public class Transf_lineales_tramos extends JFrame{
    
        private BufferedImage img;
    
    
        final int DESFACE_PUNTOS_PANEL = 44;
	final int ALTO_PANELES = 300;
	
	private VentanaTransfTramos refVTramos;
	
	public VentanaTransfTramos getRefVTramos() { return refVTramos; }
	public void setRefVTramos(VentanaTransfTramos refVTramos) { this.refVTramos = refVTramos; }

	public PanelTramos(VentanaTransfTramos refVt) {
		super();
		setRefVTramos(refVt);
	}

	public void pintarPunto (Point p) {
		if (comprobarLimite(p)) {
			Graphics gr = this.getGraphics();
			gr.setColor(Color.YELLOW);
			gr.fillOval((int) p.getX (), (int) p.getY (), 10, 10);
			gr.setColor(Color.WHITE);
			gr.drawOval((int) p.getX (), (int) p.getY (), 10, 10);
		}
	}

	public Boolean comprobarLimite (Point p) {
		return (p.getX() < 256 && p.getX() > -1 && p.getY() < 256 && p.getY() > -1);
	}

	protected void paintComponent (Graphics gr) {
		super.paintComponents(gr);
		gr.fillRect(0, 0, getWidth(), getHeight());

		// Pintamos los ejes cartesianos
		gr.setColor(Color.WHITE);

		int posXEjeX = 44;
		int posYEjeX = ALTO_PANELES - 44;

		gr.drawLine(posXEjeX, posYEjeX, this.getWidth() - 10, posYEjeX);
		gr.drawLine(posXEjeX, posYEjeX, posXEjeX, 5);

		// Numeramos los ejes
		gr.drawString(String.valueOf(0), posXEjeX, posYEjeX + 15); // (0, 0)
		gr.drawString(String.valueOf(255), this.getWidth() - 30, posYEjeX + 15); // (255, 0)
		gr.drawString(String.valueOf(255), posXEjeX - 30, 30); // (0, 255)
		

		// Pintamos las rectas entre dichos pintos
		gr.setColor(Color.RED);
		Graphics2D g2d = (Graphics2D) gr;
		g2d.setStroke(new BasicStroke(2));
		int numPuntos = getRefVTramos().getPuntosEsp().size();
		if (numPuntos >= 2) {
			for (int i = 0; i < getRefVTramos().getPuntosEsp().size() - 1; ++i) {
				g2d.drawLine((int) getRefVTramos().getPuntosEsp().get(i).getX() + DESFACE_PUNTOS_PANEL, posYEjeX - (int) getRefVTramos().getPuntosEsp().get(i).getY(), 
						(int) getRefVTramos().getPuntosEsp().get(i + 1).getX() + DESFACE_PUNTOS_PANEL, posYEjeX - (int) getRefVTramos().getPuntosEsp().get(i + 1).getY());
				
			}
		}			
					
		// Pintamos los puntos de las transformaciones
		gr.setColor(Color.YELLOW);
		for (int i = 0; i < getRefVTramos().getPuntosEsp().size(); ++i)
			gr.fillOval((int) getRefVTramos().getPuntosEsp().get(i).getX() + DESFACE_PUNTOS_PANEL - 5, posYEjeX - (int) getRefVTramos().getPuntosEsp().get(i).getY() - 5, 10, 10);
	}
        
        public void controles(){
            setTitle("Transformaciones lineales por tramos");
            setSize(300, 150);
            setResizable(false);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 1));
            add(panel);

            JLabel Gamma = new JLabel("  - Especifique los puntos para realizar las transformaciones lineales.");
    //        final JSpinner Dat_gamma = new JSpinner();

            final JTextField Dat_gamma = new JTextField();

            JButton Aceptar = new JButton("Aceptar");

            Aceptar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
    //                n_gamma = (int) Dat_gamma.getValue();
                    n_gamma = Double.parseDouble(Dat_gamma.getText());
                    dispose();
                    gestor_img.anadirImagen(gamma());
                }
        });

        panel.add(Gamma);
        panel.add(Dat_gamma);
        panel.add(Aceptar);

        setVisible(true);
    }
        
        ///////////prueba
        
        final int X = 100;
	final int Y = 30;
	final int Y_PANELES = 100;
	final int Y_TITULO = 10;
	final int ANCHO_PANELES = 300;
	final int ALTO_PANELES = 300;
	final int SEP_HOR_PANELES = 20;
	final int DESFACE_PUNTOS_PANEL = 44;
	final LineBorder BORDE = new LineBorder(Color.BLACK, 2);

	private PanelTramos panelTramos;
	private PanelImagenTramos panelImResultado;
	private ArrayList<Point> puntosEsp;
	private BufferedImage refImagenOriginal;
	private BufferedImage imagenNueva;
	private ArrayList<EcuacionRecta> ecuacionesRectas;
	private HashMap<Integer, Integer> tablaTransf;

	private VentanaPrincipal refVp; // Para eliminarla al presionar aceptar

	public PanelTramos getPanelTramos() { return panelTramos; }
	public void setPanelTramos(PanelTramos panelTramos) { this.panelTramos = panelTramos;	}

	public PanelImagenTramos getPanelImResultado() { return panelImResultado; }
	public void setPanelImResultado(PanelImagenTramos panelImResultado) { this.panelImResultado = panelImResultado; }

	public ArrayList<Point> getPuntosEsp() { return puntosEsp; }
	public void setPuntosEsp(ArrayList<Point> puntosEsp) { this.puntosEsp = puntosEsp; }

	public BufferedImage getRefImagenOriginal() { return refImagenOriginal; }
	public void setRefImagenOriginal(BufferedImage refImagenOriginal) { this.refImagenOriginal = refImagenOriginal; }

	public BufferedImage getImagenNueva() { return imagenNueva; }
	public void setImagenNueva(BufferedImage imagenNueva) { this.imagenNueva = imagenNueva; }

	public ArrayList<EcuacionRecta> getEcuacionesRectas() { return ecuacionesRectas; }
	public void setEcuacionesRectas(ArrayList<EcuacionRecta> ecuaciones) { this.ecuacionesRectas = ecuaciones; }

	public HashMap<Integer, Integer> getTablaTransf() { return tablaTransf; }
	public void setTablaTransf(HashMap<Integer, Integer> tablaTransf) { this.tablaTransf = tablaTransf; }

	public VentanaPrincipal getRefVp() { return refVp; }
	public void setRefVp(VentanaPrincipal refVp) { this.refVp = refVp; }

	public VentanaTransfTramos(BufferedImage refBfAct, VentanaPrincipal refVp) {
		setRefImagenOriginal(copiarBufferOriginal(refBfAct)); // tomamos la imagen a transformar
		setImagenNueva(copiarBufferOriginal(getRefImagenOriginal()));
		setRefVp(refVp);

		setTablaTransf(new HashMap<Integer, Integer>());
		iniciarVentana();
		iniciarPuntos();
		getPanelTramos().repaint();

		crearImagenTransformada();
		getPanelImResultado().setImagenTranf(getImagenNueva());
		getPanelImResultado().repaint();

		iniciarInsertarPuntoManual ();

	}

	public BufferedImage copiarBufferOriginal (BufferedImage original) {
		BufferedImage copia = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
		for (int i = 0; i < original.getWidth(); ++i) {
			for (int j = 0; j < original.getHeight(); ++j) {
				copia.setRGB(i, j, original.getRGB(i, j));
			}
		}
		return copia;
	}

	public void iniciarVentana () {
		setTitle("Transformaciones Lineales por tramos");
		setBounds(X, Y, (ANCHO_PANELES * 2) + (SEP_HOR_PANELES * 4), (ALTO_PANELES * 2) - (SEP_HOR_PANELES * 2));
		setLayout(null);

		// A�adimos una etiqueta con el t�tulo de la herramienta
		JLabel etiqTitulo = new JLabel(this.getTitle());
		etiqTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		etiqTitulo.setBounds(SEP_HOR_PANELES, Y_TITULO, ANCHO_PANELES * 3, Y);
		getContentPane().add (etiqTitulo);

		JLabel etiqSubTitulo = new JLabel(" - Especifique los puntos para realizar las transformaciones lineales.");
		etiqSubTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		etiqSubTitulo.setBounds(SEP_HOR_PANELES, Y * 2, ANCHO_PANELES * 3, Y);
		getContentPane().add (etiqSubTitulo);

		iniciarPanelTramos();
		iniciarPanelImResultado();

		JButton bAceptar = new JButton("Aceptar");
		bAceptar.setBounds((this.getWidth() / 2) - 30, this.getHeight() - 80, 80, 20);
		bAceptar.setBackground(Color.WHITE);
		bAceptar.setBorder(new LineBorder(Color.BLACK, 2));
		bAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getRefVp().getGestorSubVentanas().crearSubVentana(getImagenNueva(), "Especif. a trozos", false);
				dispose ();
			}
		});
		getContentPane().add (bAceptar);

		setVisible(true);
	}

	public void iniciarPanelTramos () {
		setPanelTramos(new PanelTramos(this));
		getPanelTramos().setBorder(BORDE);
		getPanelTramos().setBounds(SEP_HOR_PANELES, Y_PANELES, ANCHO_PANELES, ALTO_PANELES);

		getPanelTramos().addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent r) {
				if (r.isAltDown()) {
					getPanelTramos().pintarPunto (new Point (r.getX() - 5, r.getY() - 5));
					getPanelTramos().repaint();
				}
			}
		});	

		getPanelTramos().addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent raton) {
				try {
					if (raton.isAltDown()) {
						insertarPunto(new Point (raton.getX(), raton.getY()), true);

						modificarEcuacionesRectas ();
						getPanelTramos().repaint();
						rellenarTablaDeTransformacion();
						crearImagenTransformada();
						getPanelImResultado().repaint();

					} else  {

						int ind = buscarCoincidenteEnX(new Point (raton.getX() - DESFACE_PUNTOS_PANEL, raton.getY()));

						if (getPuntosEsp().size() > 1 && ind > 0 && ind != getPuntosEsp().size() - 1) {
							eliminarPunto (ind);

							modificarEcuacionesRectas ();
							getPanelTramos().repaint();
							rellenarTablaDeTransformacion();
							crearImagenTransformada();
							getPanelImResultado().repaint();
						}
					}
				} catch (Exception e) { }
			}
		});
		getContentPane().add(getPanelTramos());
	}

	public void iniciarPanelImResultado () {
		setPanelImResultado(new PanelImagenTramos(getImagenNueva())); // inicialmente pinta la original

		getPanelImResultado().setBorder(BORDE);
		getPanelImResultado().setBounds(this.getWidth() - SEP_HOR_PANELES * 2 - ANCHO_PANELES,
				Y_PANELES, ANCHO_PANELES, ALTO_PANELES);
		getContentPane().add(getPanelImResultado());
	}

	public void iniciarInsertarPuntoManual () {
		JLabel etiqPunto = new JLabel("Insertar Punto manualmente: ");
		etiqPunto.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		etiqPunto.setBounds(getPanelTramos().getX(), getPanelTramos().getY() + getPanelTramos().getHeight() + 10, 300, 20);
		getContentPane().add (etiqPunto);

		JLabel etiqX = new JLabel("X = ");
		etiqX.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		etiqX.setBounds(getPanelTramos().getX() + 20, getPanelTramos().getY() + getPanelTramos().getHeight() + 40, 30, 20);
		getContentPane().add (etiqX);

		final JTextField textoX = new JTextField();
		textoX.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		textoX.setBounds(getPanelTramos().getX() + 60, getPanelTramos().getY() + getPanelTramos().getHeight() + 40, 30, 20);
		getContentPane().add (textoX);

		JLabel etiqY = new JLabel("Y = ");
		etiqY.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		etiqY.setBounds(getPanelTramos().getX() + 20, getPanelTramos().getY() + getPanelTramos().getHeight() + 65, 30, 20);
		getContentPane().add (etiqY);

		final JTextField textoY = new JTextField();
		textoY.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		textoY.setBounds(getPanelTramos().getX() + 60, getPanelTramos().getY() + getPanelTramos().getHeight() + 65, 30, 20);
		getContentPane().add (textoY);

		JButton btnInsertrPunto = new JButton("Insertar");
		btnInsertrPunto.setBorder(new LineBorder(Color.BLACK, 2));
		btnInsertrPunto.setBackground(Color.WHITE);
		btnInsertrPunto.setForeground(Color.BLACK);
		btnInsertrPunto.setBounds(textoY.getX() + 50, textoY.getY() - 20, 100, 30);

		btnInsertrPunto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (textoX.getText().length() > 0 && textoY.getText().length() > 0) {
					try {
						Point pTemp = new Point(Integer.valueOf(textoX.getText()), Integer.valueOf(textoY.getText()));
						insertarPunto(pTemp, false);
						modificarEcuacionesRectas ();
						getPanelTramos().repaint();
						rellenarTablaDeTransformacion();
						crearImagenTransformada();
						getPanelImResultado().repaint();
						
					} catch (Exception e) {
						
						final JDialog error = new JDialog();
						
						JLabel mensaje = new JLabel("ERROR: Indique la X y la Y del punto como un N�MERO entre 0 y 255 !!");
						mensaje.setLocation(50, 10);
						error.setSize(450, 150);
						error.setLocation(200, 200);
						error.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						error.setAlwaysOnTop(true);
						error.add(mensaje);
						error.setLayout(null);
						
						JButton acept = new JButton("Aceptar");
						acept.setSize(100, 25);
						acept.setLocation(150, 70);
						acept.setBackground(Color.WHITE);
						acept.setForeground(Color.BLACK);
						acept.setBorder(new LineBorder(Color.BLACK, 2));
						acept.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								error.dispose ();
							}
						});
						error.getContentPane().add (acept);
						
						error.setVisible(true);
					}
				}
			}
		});

		getContentPane().add (btnInsertrPunto);
	}

	private void iniciarPuntos () {
		setPuntosEsp(new ArrayList<Point>());
		getPuntosEsp().add(new Point(0, 0));
		getPuntosEsp().add(new Point(255, 255));

		setEcuacionesRectas(new ArrayList<EcuacionRecta>());

		EcuacionRecta primera = new EcuacionRecta(getPuntosEsp().get(0), getPuntosEsp().get(1));
		primera.rellenarEcuacion();
		getEcuacionesRectas().add(primera);
	}

	public void insertarPunto (Point q, Boolean desfacePunto) {
		Point p = null;
		if (desfacePunto)
			p = new Point ((int) Math.round(q.getX() - DESFACE_PUNTOS_PANEL), 
					(int) Math.round(ALTO_PANELES - DESFACE_PUNTOS_PANEL - q.getY() - 1));
		else 
			p = q;

		System.out.println("Punto ["+p.getX() + " , " + p.getY() + "]");
		if (getPanelTramos().comprobarLimite(p)) {

			// Comprobar si coincide en x con alg�n punto (menos 1� y �ltimo)
			int coincideX = buscarCoincidenteEnX(p);

			// si -> intercambiar y del punto (menos 1� y �ltimo)
			if (coincideX != 0 && coincideX != getPuntosEsp().size() - 1) {
				if (coincideX != -1) {
					getPuntosEsp().get(coincideX).setLocation(getPuntosEsp().get(coincideX).getX(), p.getY());

				} else { 
					int puntoAnterior = buscarAnteriorPunto(p); // Comprobar entre que par de puntos est� la x
					ArrayList<Point> puntosOrdenados = insertarPuntoOrdenado(p, puntoAnterior);

					// Hacer que �ste sea el nuevo vector de puntos de la clase
					getPuntosEsp().clear();
					setPuntosEsp(puntosOrdenados);
				}

			} else if (coincideX == 0) { // Si coincide X del primer punto -> cambiamos su Y
				getPuntosEsp().get(0).setLocation(getPuntosEsp().get(0).getX(), p.getY());
				modificarEcuacionesRectas();
				rellenarTablaDeTransformacion();
				crearImagenTransformada();

			} else if (coincideX == getPuntosEsp().size() - 1) { // Si coincide X del �ltimo punto -> cambiamos su Y
				getPuntosEsp().get(getPuntosEsp().size() - 1).setLocation(getPuntosEsp().get(getPuntosEsp().size() - 1).getX(), p.getY());
				modificarEcuacionesRectas();
				rellenarTablaDeTransformacion();
				crearImagenTransformada();
			}
		}
		getPanelImResultado().repaint();
	}

	public int buscarCoincidenteEnX (Point p) {
		int DIST_X = 5;
		for (int i = 0; i < getPuntosEsp().size(); ++i)
			if (Math.abs (p.getX() - getPuntosEsp().get(i).getX()) <= DIST_X)
				return i;
		return -1;
	}

	public ArrayList<Point> insertarPuntoOrdenado (Point p, int puntoAnterior) {
		ArrayList<Point> puntosTemp = new ArrayList<Point>();

		for (int i = 0; i <= puntoAnterior; ++i) // Metemos los puntos anteriores al nuevo que vamos a insertar (inclu�do el anterior)
			puntosTemp.add(getPuntosEsp().get(i));

		puntosTemp.add(new Point ((int) p.getX(), (int) p.getY())); // Meter punto nuevo en el vector

		// Meter resto de puntos
		for (int i = puntoAnterior + 1; i < getPuntosEsp().size(); ++i) { 
			puntosTemp.add(getPuntosEsp().get(i));
		}

		return puntosTemp;
	}

	public int buscarAnteriorPunto (Point p) {
		int anterior = 0;
		for (int i = 1; i < getPuntosEsp().size() - 1; ++i) {
			if (p.getX() > getPuntosEsp().get(i).getX())
				anterior = i;
		}
		return anterior;
	}

	public void modificarEcuacionesRectas () {
		if (!getEcuacionesRectas().isEmpty())
			getEcuacionesRectas().clear();

		for (int i = 0; i < getPuntosEsp().size() - 1; ++i) {
			Point a = getPuntosEsp().get(i);

			Point b = getPuntosEsp().get(i + 1);
			EcuacionRecta ec = new EcuacionRecta(a, b);
			ec.rellenarEcuacion();
			getEcuacionesRectas().add(ec);
		}

	}

	public void rellenarTablaDeTransformacion () {
		int inicioRect = 0, finRect = 0;
		for (int i = 0; i < getEcuacionesRectas().size(); ++i) {
			inicioRect = (int) getEcuacionesRectas().get(i).getIni().getX();
			finRect = (int) getEcuacionesRectas().get(i).getFin().getX();
			for (int j = inicioRect; j < finRect + 1; ++j)
				getTablaTransf().put(j, getEcuacionesRectas().get(i).getRecta().get(j));
		}

	}

	public void crearImagenTransformada () {
		if (getRefImagenOriginal() != null && getTablaTransf() != null && getTablaTransf().size() > 0) {
			int argb = 0;
			for (int i = 0; i < getImagenNueva().getWidth(); ++i) {
				for (int j = 0; j < getImagenNueva().getHeight(); ++j) {
					argb = getTablaTransf().get(new Color(getRefImagenOriginal().getRGB(i, j)).getRed());
					getImagenNueva().setRGB(i, j, new Color(argb, argb, argb).getRGB());
				}
			}
		}
		getPanelImResultado().setImagenTranf(getImagenNueva());
	}

	public void eliminarPunto (int pos) {
		ArrayList<Point> temp = new ArrayList<>();
		for (int i = 0; i < getPuntosEsp().size(); ++i) {
			if (i != pos)
				temp.add (new Point (getPuntosEsp().get(i)));
		}
		setPuntosEsp(temp); // Sobreescribimos el vector de puntos oficial
	}
        
public class EcuacionRecta {
        private HashMap<Integer, Integer> recta;
	int indiceIni = 0;
	int indiceFin = 0;
	private Point ini;
	private Point fin;
	
	public HashMap<Integer, Integer> getRecta() { return recta; }
	public void setRecta(HashMap<Integer, Integer> recta) { this.recta = recta; }

	public int getIndiceIni() { return indiceIni; }
	public void setIndiceIni(int indiceIni) { this.indiceIni = indiceIni; }
	
	public int getIndiceFin() { return indiceFin; }
	public void setIndiceFin(int indiceFin) { this.indiceFin = indiceFin; }
	
	public Point getIni() { return ini;	}
	public void setIni(Point ini) { this.ini = ini; }
	
	public Point getFin() { return fin;	}
	public void setFin(Point fin) { this.fin = fin;	}
	
	public EcuacionRecta(Point ini, Point fin) { 
		setRecta(new HashMap<Integer, Integer>()); 
		
		setIni(ini);
		setFin(fin);
		
		setIndiceIni((int) Math.round(getIni().getX()));
		setIndiceFin((int) Math.round(getFin().getX()));
	}
        public double calcularPendiente () {	
		if (getFin().getX() == getIni().getX())
			return 1.0f;
		return ((getFin().getY() - getIni().getY()) / (getFin().getX() - getIni().getX()));
	}
	
	public void rellenarEcuacion () {
		double m = calcularPendiente();
		
		int n = (int) Math.round (getIni().getY() - (m * (getIni().getX())));
		for (int x = getIndiceIni(); x < getIndiceFin() + 1; ++x) {
			getRecta().put(x, (int) Math.round(m * x + n));
		}
	}
    
    }
    }


}


