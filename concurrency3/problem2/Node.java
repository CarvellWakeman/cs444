/*
 *  Java Program to Implement Singly Linked List
 *  CREDIT: This singly linked list was not implemented by me, it was found at http://www.sanfoundry.com/java-program-implement-singly-linked-list/
 *  This singly linked list was implemented by Manish Bhojasia
 */

/*  Class Node  */
public class Node
{
	protected String data;
	protected Node link;

	/*  Constructor  */
	public Node()
	{
		link = null;
		data = "";
	}
	/*  Constructor  */
	public Node(String d,Node n)
	{
		data = d;
		link = n;
	}
	/*  Function to set link to next Node  */
	public void setLink(Node n)
	{
		link = n;
	}
	/*  Function to set data to current Node  */
	public void setData(String d)
	{
		data = d;
	}
	/*  Function to get link to next node  */
	public Node getLink()
	{
		return link;
	}
	/*  Function to get data from current Node  */
	public String getData()
	{
		return data;
	}
}
