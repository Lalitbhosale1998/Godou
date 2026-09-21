package com.personal.godou.di;

import com.personal.godou.data.dao.VocabDao;
import com.personal.godou.data.database.GodouDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AppModule_ProvideVocabDaoFactory implements Factory<VocabDao> {
  private final Provider<GodouDatabase> databaseProvider;

  private AppModule_ProvideVocabDaoFactory(Provider<GodouDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VocabDao get() {
    return provideVocabDao(databaseProvider.get());
  }

  public static AppModule_ProvideVocabDaoFactory create(Provider<GodouDatabase> databaseProvider) {
    return new AppModule_ProvideVocabDaoFactory(databaseProvider);
  }

  public static VocabDao provideVocabDao(GodouDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVocabDao(database));
  }
}
