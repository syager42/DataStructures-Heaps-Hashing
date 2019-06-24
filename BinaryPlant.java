public class BinaryPlant<E>{
	private PlantNode<E> overallRoot;
	
	public BinaryPlant(int max) {
		overallRoot = buildPlant(1, max);
	}
	
	private PlantNode buildPlant(int n, int max) {
		if(n>max) {
			return null;
		} else {
			return new PlantNode(n, buildPlant(2*n, max), buildPlant(2*n+1, max));
		}
	}
	
	private BinaryPlant() {
		
	}
	
	public void printPreorder() {
		System.out.print("Preorder: ");
		printPreorder(overallRoot);
		System.out.println();
	}
	
	private void printPreorder(PlantNode<E> root) {
		if (root != null) {
			System.out.print(" " + root.data);
			printPreorder(root.left);
			printPreorder(root.right);
		}
	}
	
	public void printPostorder() {
		System.out.print("Postorder: ");
		printPostorder(overallRoot);
		System.out.println();
	}
	
	private void printPostorder(PlantNode<E> root) {
		if (root != null) {
			printPostorder(root.left);
			printPostorder(root.right);
			System.out.print(" " + root.data);
		}
	}
	
	public void printInorder() {
		System.out.print("Inorder: ");
		printInorder(overallRoot);
		System.out.println();
	}
	
	private void printInorder(PlantNode<E> root) {
		if (root != null) {
			printInorder(root.left);
			System.out.print(" " + root.data);
			printInorder(root.right);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static class PlantNode<E> {
		public E data;
		public PlantNode<E> left;
		public PlantNode<E> right;
		
		public PlantNode(E data) {
			this(data, null, null);
		}
		
		@SuppressWarnings("unchecked")
		public PlantNode(E data, PlantNode<E> left, PlantNode<E> right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
	}
	
	public static void main(String[] args) {
		BinaryPlant plant1 = new BinaryPlant(13);
		plant1.printPreorder();
		plant1.printPostorder();
		plant1.printInorder();
	}
	
}