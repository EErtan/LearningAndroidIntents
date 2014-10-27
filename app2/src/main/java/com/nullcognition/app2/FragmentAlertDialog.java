package com.nullcognition.app2;
public class FragmentAlertDialog extends android.app.DialogFragment implements android.content.DialogInterface.OnClickListener {

  private static final String POSITIVE_KEY = "positive";
  private static final String NEGATIVE_KEY = "negative";
  private static final String TITLE_KEY    = "title";
  private static final String MESSAGE_KEY  = "message";
  private int idPositive;
  private int idNegative;
  private int idTitle;
  private int idMessage;

  public FragmentAlertDialog(){}

  public static com.nullcognition.app2.FragmentAlertDialog newInstance(int inTextIdPositive, int inTextIdNegative, int inTextIdTitle,
																	   int inTextIdMessage){
	com.nullcognition.app2.FragmentAlertDialog fragment = new com.nullcognition.app2.FragmentAlertDialog();
	android.os.Bundle args = new android.os.Bundle();
	args.putInt(POSITIVE_KEY, inTextIdPositive);
	args.putInt(NEGATIVE_KEY, inTextIdNegative);
	args.putInt(TITLE_KEY, inTextIdTitle);
	args.putInt(MESSAGE_KEY, inTextIdMessage);
	fragment.setArguments(args);
	return fragment;
  }

  @Override
  public void onCreate(android.os.Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	if(getArguments() != null){
	  idPositive = getArguments().getInt(POSITIVE_KEY);
	  idNegative = getArguments().getInt(NEGATIVE_KEY);
	  idTitle = getArguments().getInt(TITLE_KEY);
	  idMessage = getArguments().getInt(MESSAGE_KEY);
	}
  }

  @Override
  public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState){
	super.onCreateDialog(savedInstanceState);
	android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
	builder.setTitle(idTitle).setIcon(android.R.drawable.ic_menu_edit).setPositiveButton(idPositive, this).setNegativeButton(idNegative, this);
	if(idMessage != 0){builder.setMessage(idMessage);}
	android.app.AlertDialog alertDialog = builder.create();
	return alertDialog;
  }

  @Override
  public void onAttach(android.app.Activity activity){
	super.onAttach(activity);
	try{
	  fragmentAlertDialogInteractionListener = (com.nullcognition.app2.FragmentAlertDialog.OnFragmentAlertDialogInteractionListener)activity;
	}
	catch(ClassCastException e){
	  throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
	}
  }

  @Override
  public void onDetach(){
	super.onDetach();
	fragmentAlertDialogInteractionListener = null;
  }

  private com.nullcognition.app2.FragmentAlertDialog.OnFragmentAlertDialogInteractionListener fragmentAlertDialogInteractionListener;

  public interface OnFragmentAlertDialogInteractionListener {

	public void onAlertDialogFragmentInteraction(int buttonClicked);
  }

  @Override
  public void onClick(android.content.DialogInterface dialog, int which){
	final int positive = - 1, negative = - 2;
	switch(which){
	  case positive:
		onButtonPressed(1);
		break;
	  case negative:
		onButtonPressed(0);
		break;
	  default:
		android.util.Log.e(getClass().getSimpleName(), "FragmentDialag.onClick switch default");
		throw new java.security.InvalidParameterException();
	}
  }

  public void onButtonPressed(int buttonClicked){
	if(fragmentAlertDialogInteractionListener != null){
	  fragmentAlertDialogInteractionListener.onAlertDialogFragmentInteraction(buttonClicked);
	}
  }

}
