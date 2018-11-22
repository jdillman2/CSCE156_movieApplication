package lists;

import java.util.Comparator;

import entities.Invoice;

public class TotalComparator<E> implements Comparator<E>{

	//Returns a value greater than 0 if the first argument has a larger grand total than the second argument
	//Returns a value less than 0 if the second argument has a larger grand total than the first argument
	@Override
	public int compare(E arg0, E arg1) {
		// TODO Auto-generated method stub
		return (int) (Math.round(((Invoice) arg0).getInvoiceGrandTotal()) - Math.round(((Invoice) arg1).getInvoiceGrandTotal()));
	}
	
}
