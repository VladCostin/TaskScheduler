package DatabaseOperation;

import com.example.meniu.MainActivity;
import com.example.meniu.ShowAllTasks;
import com.example.meniu.ShowTasks;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * erases the task associated with the buton pushed 
 * @author ${Vlad Herescu}
 *
 */
public class EraseTask implements OnClickListener  {
	
	
	/**
	 * the activity containing the id of the tasks
	 */
	private ShowAllTasks show;

	public EraseTask(ShowAllTasks showAllTasks)
	{
		show = showAllTasks;
			
	}
	
	@Override
	public void onClick(View arg0) {
		
		Integer idTask = show.getIdTasks().get(arg0.getId());
		MainActivity.getDatabase().deleteContact(idTask);
		show.showUpdate();
		
	}



}
