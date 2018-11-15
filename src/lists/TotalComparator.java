package lists;

import java.util.Comparator;

import entities.Invoice;

public class TotalComparator<E> implements Comparator<E>{

	@Override
	public int compare(E arg0, E arg1) {
		// TODO Auto-generated method stub
		return (int) (Math.round(((Invoice) arg0).getInvoiceGrandTotal()) - Math.round(((Invoice) arg1).getInvoiceGrandTotal()));
	}
	
}
