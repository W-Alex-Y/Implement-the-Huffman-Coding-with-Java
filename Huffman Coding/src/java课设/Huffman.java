/**
Huffman树的实现
*/
package java课设;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Huffman extends JFrame {
	private FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 60, 10);
	private List<String> nums;//临时储存要进行编码的这组数，会随着后面的调用而变化
	private List<String> numsMo;//储存要进行编码的一组数
	private List<Tree> trees;
	private Vector<Node> nodes;
	private Tree root;
	private String temp;
	private Map<String, Integer> pMap;
	private Integer length;
	private Integer height;
	private Integer count;
	private Set<String> keySet;
	private JPanel jpl = new JPanel();
	private JLabel jl = new JLabel(new ImageIcon("/Users/wy/Desktop/Huffman Coding.png"));
	private JButton bScanf = new JButton("自行输入");
	private JButton bFile = new JButton("文件读取");
	private JButton bD = new JButton("动画演示");
	private JButton bH = new JButton("手动演示");
	private JButton bExit = new JButton("退出");
	private JTextArea taInfo = new JTextArea(30, 28);
	
	public Huffman() {
		nums = new ArrayList<String>();
		numsMo = new ArrayList<String>();
		trees = new ArrayList<Tree>();
		pMap = new HashMap<String,Integer>();
		nodes = new Vector<Node>();
		root = new Tree();
		height = 0;
		count = 0;
		taInfo.setText("");
		jpl.setLayout(flowLayout);
		setTitle("Huffman编码算法演示");
		jl.setBounds(0, 0, jpl.getWidth(), jpl.getHeight());
		jpl.add(jl);
		jpl.add(bScanf);
		jpl.add(bFile);
		jpl.add(bD);
		jpl.add(bH);
		jpl.add(taInfo);
		taInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		jpl.add(bExit, BorderLayout.SOUTH);
		
		bScanf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nums = new ArrayList<String>();
				numsMo = new ArrayList<String>();
				trees = new ArrayList<Tree>();
				pMap = new HashMap<String,Integer>();
				nodes = new Vector<Node>();
				root = new Tree();
				height = 0;
				count = 0;
				taInfo.setText("");
				String str = JOptionPane.showInputDialog("请输入您所需要编码的字符串：");
				if(str==null || "".equalsIgnoreCase(str)){
					JOptionPane.showMessageDialog(null, "输入不能为空！请重新输入");
				}
				addNums(str);
				compareNum(nums, trees);
				while(numsMo.size()>1){
					String[] mins = minTwo(numsMo);
					numsMo.remove(mins[0]);
					write(mins[0]);
				}
				if(!numsMo.isEmpty()){
					write(numsMo.get(0));
				}
				taInfo.append("\t" + "二叉树的高度为：" + height + "\n");
				for(Tree t : trees) {//找根
					if(t.getParents().length() == count) {
						root = t;
						draw(root, 1, 1);
						break;
					}
				}
				for(Node i : nodes) {
					int x = i.xNode;
					int y = i.yNode;
					int cnt = (int)Math.pow(2, y-1);
					i.xNode = x * 1400 / (cnt+1)-100; 
					i.yNode = 600 * y / (height+2);
				}
			}
		});
		
		bFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nums = new ArrayList<String>();
				numsMo = new ArrayList<String>();
				trees = new ArrayList<Tree>();
				pMap = new HashMap<String,Integer>();
				nodes = new Vector<Node>();
				root = new Tree();
				height = 0;
				count = 0;
				taInfo.setText("");
				String srcFilename = JOptionPane.showInputDialog("请输入您要读取的文件路径");
				try {
					FileInputStream fis = new FileInputStream(srcFilename);
					InputStreamReader isr = new InputStreamReader(fis);
					BufferedReader br = new BufferedReader(isr);
					String str = "";
					String sTemp;
					while((sTemp=br.readLine())!=null) {
						str += sTemp;
					}
					addNums(str);
					compareNum(nums, trees);
					while(numsMo.size()>1) {
						String[] mins = minTwo(numsMo);
						numsMo.remove(mins[0]);
						write(mins[0]);
					}
					if(!numsMo.isEmpty()) {
						write(numsMo.get(0));
					}
					fis.close();
					taInfo.append("\t" + "二叉树的高度为：" + height + "\n");
					for(Tree t : trees) {
						if(t.getParents().length() == count) {
							root = t;
							draw(root, 1, 1);
							break;
						}
					}
					for(Node i : nodes) {
						int x = i.xNode;
						int y = i.yNode;
						int cnt = (int)Math.pow(2, y-1);
						i.xNode = x * 1400 / (cnt+1)-100; 
						i.yNode = 600 * y / (height+2);
					}
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "操作异常，请重新操作");
				}
			}
		});
		
		bD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String T = JOptionPane.showInputDialog("请设定生成二叉树的时间间隔(以毫秒为单位)");
				new AutoImage(nodes, trees, Integer.parseInt(T));
			}
		});
		
		bH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HandImage(nodes, trees, count);
			}
		});
		
		bExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		this.add(jpl);
		this.setVisible(true);
		this.setSize(400, 810);
		this.setLocation(520, 0);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void addNums(String str) {
		String[] split = str.split("");
		length = str.length();
		for (int i = 0; i < length; i++) {
			if(!"".equals(split[i]) && pMap.containsKey(split[i])) {
				pMap.put(split[i], pMap.get(split[i])+1);
			}else if(!"".equals(split[i])) {
				pMap.put(split[i], 1);
				count++;
			}
		}
		keySet = pMap.keySet();
		for (String string : keySet) {
			nums.add(string);
			numsMo.add(string);
		}
	}
	
	public void compareNum(List<String> nums, List<Tree>trees) {
		String[] min = new String[2];
		if(nums.size()>1) {
			min = minTwo(nums);
			String s = min[0] + min[1];
			Tree t = new Tree(min[0], min[1], s);
			nums.remove(min[0]);
			nums.remove(min[1]);
			nums.add(min[0]+min[1]);
			pMap.put(min[0]+min[1], pMap.get(min[0])+pMap.get(min[1]));
			trees.add(t);
			
			compareNum(nums, trees);
		}
	}

	public void print(String num) {
		for(Tree t : trees) {
			if(num.equals(t.getRchild())) {
				temp = 1+temp;
				print(t.getParents());
				break;
			}else if(num.equals(t.getLchild())) {
				temp = 0+temp;
				print(t.getParents());
				break;
			}
		}
	}
	
	public void write(String d) {
		temp = "";
		taInfo.append("      " + d + "的编码为：");
		print(d);
		taInfo.append(temp + "  码长为：" + temp.length() + "  ");
		height = Math.max(height, temp.length());
		double p = Math.round((double)pMap.get(d) / length * 1000);
		taInfo.append("出现概率为：" + p/1000 + "\n\n");
	}
	
	public String[] minTwo(List<String> nums) {
		String temp;
		for (int i = 0; i < 2; i++) {
			for (int  j = 1; j < nums.size(); j++) {
				if (pMap.get(nums.get(j-1)) < pMap.get(nums.get(j))) {
					temp = nums.get(j);
					nums.set(j, nums.get(j-1));
					nums.set(j-1, temp);
				}
			}
		}
		String[] n = {nums.get(nums.size()-1), nums.get(nums.size()-2)};		
		return n;
	}
	
	public void draw(Tree root, int x, int y) {//节点坐标确定并存入里边  
		Node node = new Node(root.getParents(), x, y);
		nodes.add(node);
		int ok = 0;
        if(root.getLchild()!=null) { 
	    		for(Tree t : trees) {
				if(t.getParents().equals(root.getLchild())) {
					draw(t, 2*x-1, y+1);  
					ok = 1;
					break;
				}
			}
	    		if(ok == 0) {
	        		node = new Node(root.getLchild(), 2*x-1, y+1);
	        		nodes.add(node);
	        }
        }  
        ok = 0;
        if(root.getRchild()!=null) {  
        		for(Tree t : trees) {
				if(t.getParents().equals(root.getRchild())) {
					draw(t, 2*x, y+1);
					ok = 1;
					break;
				}
			}	
        		if(ok == 0) {
	        		node = new Node(root.getRchild(), 2*x, y+1);
	        		nodes.add(node);
	        }
        }  
    }  
	
	public static void main(String[] args) {
		new Huffman();
	}
}

