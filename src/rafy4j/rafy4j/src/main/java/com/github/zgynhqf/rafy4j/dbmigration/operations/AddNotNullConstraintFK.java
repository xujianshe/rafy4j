package com.github.zgynhqf.rafy4j.dbmigration.operations;

public class AddNotNullConstraintFK extends ColumnOperation
{
	@Override
	protected void Down()
	{
		RemoveNotNullConstraintFK op = new RemoveNotNullConstraintFK();
		op.copyFrom(this);
		this.AddOperation(op);
	}
}